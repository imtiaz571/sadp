/**
 * SkyWay 3D Aircraft Viewer - FBX Edition
 * Loads an external FBX model for the aircraft
 */

// ============ INIT ============
function initPlaneViewer(containerId, options = {}) {
    const container = document.getElementById(containerId);
    if (!container) return;

    const width = container.clientWidth;
    const height = options.height || 500;
    container.style.height = height + 'px';
    
    var autoRotate = false; // Using var to avoid TDZ issues in callbacks

    // ── Scene ──
    const scene = new THREE.Scene();
    scene.background = new THREE.Color(0xf2f2f2);
    scene.fog = new THREE.Fog(0xf2f2f2, 100, 300);

    // ── Camera ──
    const camera = new THREE.PerspectiveCamera(45, width / height, 0.1, 2000);
    camera.position.set(50, 30, 60);
    camera.lookAt(0, 0, 0);

    // ── Renderer ──
    const renderer = new THREE.WebGLRenderer({ antialias: true, alpha: true });
    renderer.setSize(width, height);
    renderer.setPixelRatio(1); // Cap pixel ratio to 1 for massive performance boost during scrolling
    renderer.shadowMap.enabled = true;
    renderer.shadowMap.type = THREE.PCFSoftShadowMap;
    renderer.outputEncoding = THREE.sRGBEncoding;
    renderer.toneMapping = THREE.ACESFilmicToneMapping;
    renderer.toneMappingExposure = 1.0;
    container.appendChild(renderer.domElement);

    // ── Lights ──
    const ambientLight = new THREE.AmbientLight(0xffffff, 0.5);
    scene.add(ambientLight);

    const dirLight = new THREE.DirectionalLight(0xffffff, 1.0);
    dirLight.position.set(50, 100, 50);
    dirLight.castShadow = true;
    dirLight.shadow.mapSize.set(1024, 1024); // Lowered from 2048 to save GPU resources
    dirLight.shadow.camera.near = 1;
    dirLight.shadow.camera.far = 500;
    dirLight.shadow.camera.left = -100;
    dirLight.shadow.camera.right = 100;
    dirLight.shadow.camera.top = 100;
    dirLight.shadow.camera.bottom = -100;
    scene.add(dirLight);

    const fillLight = new THREE.DirectionalLight(0x8888ff, 0.2);
    fillLight.position.set(-50, 20, -50);
    scene.add(fillLight);

    // ── Ground ──
    const groundGeo = new THREE.CircleGeometry(200, 64);
    const groundMat = new THREE.MeshStandardMaterial({ 
        color: 0xe8e8e8, 
        roughness: 0.9,
        metalness: 0 
    });
    const ground = new THREE.Mesh(groundGeo, groundMat);
    ground.rotation.x = -Math.PI / 2;
    ground.position.y = -20;
    ground.receiveShadow = true;
    scene.add(ground);

    const grid = new THREE.GridHelper(200, 40, 0xdddddd, 0xeeeeee);
    grid.position.y = -19.99;
    scene.add(grid);

    // ── Aircraft Group ──
    const aircraft = new THREE.Group();
    scene.add(aircraft);

    // ── LOAD FBX MODEL ──
    const loader = new THREE.FBXLoader();
    const modelPath = window.location.origin + '/model/untitled.fbx'; 

    loader.load(modelPath, (object) => {
        object.traverse(function (child) {
            if (child.isMesh) {
                child.castShadow = true;
                child.receiveShadow = true;
                
                // Enhance materials if they are basic
                if (child.material) {
                    if (Array.isArray(child.material)) {
                        child.material.forEach(m => {
                            m.roughness = 0.4;
                            m.metalness = 0.3;
                        });
                    } else {
                        child.material.roughness = 0.4;
                        child.material.metalness = 0.3;
                    }
                }
            }
        });

        // The bounding box dimensions were artificially inflated by stray nodes, 
        // so we use a fixed scale. However, we MUST use the bounding box center 
        // to move the plane back to the origin, as the FBX was exported off-center.
        const box = new THREE.Box3().setFromObject(object);
        const center = box.getCenter(new THREE.Vector3());

        const scale = 1.2;
        object.scale.setScalar(scale);
        
        // Center the geometry at (0,0,0) then drop it down slightly
        object.position.sub(center.clone().multiplyScalar(scale));
        object.position.y -= 8;

        aircraft.add(object);
        
        // Auto-rotate initialization
        autoRotate = true;
        
        console.log('FBX Model loaded successfully with hardcoded scale and position');
    }, (xhr) => {
        if (xhr.lengthComputable) {
            const percentComplete = xhr.loaded / xhr.total * 100;
            console.log(Math.round(percentComplete, 2) + '% downloaded');
        }
    }, (error) => {
        console.error('FBX Load Error for ' + modelPath + ':', error);
        if (error.message) console.error('Error message:', error.message);
        if (error.stack) console.error('Error stack:', error.stack);
        
        // Fallback to a simple box if it fails
        const fallbackGeo = new THREE.BoxGeometry(30, 5, 5);
        const fallbackMat = new THREE.MeshStandardMaterial({ color: 0xcccccc });
        const fallback = new THREE.Mesh(fallbackGeo, fallbackMat);
        aircraft.add(fallback);
    });

    // ── ORBIT CONTROLS ──
    const controls = new THREE.OrbitControls(camera, renderer.domElement);
    controls.enableDamping = true;
    controls.dampingFactor = 0.08;
    controls.minDistance = 10;
    controls.maxDistance = 500;
    controls.target.set(0, 0, 0);

    // ── CAMERA PRESETS ──
    const presets = {
        exterior: { pos: [60, 30, 80], target: [0, 0, 0] },
        front: { pos: [80, 10, 0], target: [0, 0, 0] },
        top: { pos: [0, 100, 0.1], target: [0, 0, 0] },
        side: { pos: [0, 10, 80], target: [0, 0, 0] },
        interior: { pos: [5, 2, 5], target: [0, 0, 0] },
        cockpit: { pos: [35, 2, 0], target: [45, 1, 0] },
        tail: { pos: [-60, 20, 40], target: [-30, 5, 0] }
    };

    function animateCamera(preset) {
        const p = presets[preset];
        if (!p) return;
        const startPos = camera.position.clone();
        const endPos = new THREE.Vector3(...p.pos);
        const startTarget = controls.target.clone();
        const endTarget = new THREE.Vector3(...p.target);
        const duration = 1200;
        const startTime = Date.now();

        function tweenStep() {
            const elapsed = Date.now() - startTime;
            const t = Math.min(elapsed / duration, 1);
            const ease = 1 - Math.pow(1 - t, 3); // easeOutCubic

            camera.position.lerpVectors(startPos, endPos, ease);
            controls.target.lerpVectors(startTarget, endTarget, ease);
            controls.update();

            if (t < 1) requestAnimationFrame(tweenStep);
        }
        tweenStep();
    }

    window.planeViewPreset = animateCamera;
    
    controls.addEventListener('start', () => { autoRotate = false; });

    // ── INTERSECTION OBSERVER (Pause Rendering When Scrolled Out of View) ──
    let isVisible = true;
    const observer = new IntersectionObserver((entries) => {
        entries.forEach(entry => {
            isVisible = entry.isIntersecting;
        });
    });
    observer.observe(renderer.domElement);

    // ── ANIMATION LOOP ──
    let time = 0;
    function animate() {
        requestAnimationFrame(animate);
        
        // Skip heavy GPU rendering if the user scrolled past the viewer
        if (!isVisible) return;

        time += 0.005;

        if (autoRotate && aircraft.children.length > 0) {
            aircraft.rotation.y = Math.sin(time * 0.3) * 0.1;
        }

        controls.update();
        renderer.render(scene, camera);
    }
    animate();

    window.addEventListener('resize', () => {
        const w = container.clientWidth;
        camera.aspect = w / height;
        camera.updateProjectionMatrix();
        renderer.setSize(w, height);
    });

    return { scene, camera, renderer, controls, aircraft, presets: animateCamera };
}
