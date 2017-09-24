var container;

var camera, scene, renderer;

var mouseX = 0, mouseY = 0;

var windowHalfX = window.innerWidth / 2;
var windowHalfY = window.innerHeight / 2;
var pngpath = $("#pngpath").val();
var objpath = $("#objpath").val();
/*
* 如果处理成功，则显示模型
* */
if($("#handled").val() == "true")
    showModel();

function showModel(){
    init();
    animate();
}

function init() {

    container = document.getElementById( 'obj_div' );
    camera = new THREE.PerspectiveCamera( 45, window.innerWidth / window.innerHeight, 1, 2000 );
    camera.position.z = 350;
    scene = new THREE.Scene();
    var ambient = new THREE.AmbientLight( 0x101030 );
    scene.add( ambient );
    var directionalLight = new THREE.DirectionalLight( 0xffeedd );
    directionalLight.position.set( 0, 0, 1 );
    scene.add( directionalLight );
    var manager = new THREE.LoadingManager();
    manager.onProgress = function ( item, loaded, total ) {
        console.log( item, loaded, total );
    };
    var texture = new THREE.Texture();
    var onProgress = function ( xhr ) {
        if ( xhr.lengthComputable ) {
            var percentComplete = xhr.loaded / xhr.total * 100;
            console.log( Math.round(percentComplete, 2) + '% downloaded' );
        }
    };
    var onError = function ( xhr ) {
    };
    var loader = new THREE.ImageLoader( manager );
    loader.load( pngpath, function ( image ) {
        texture.image = image;
        texture.needsUpdate = true;
    } );
    var loader = new THREE.OBJLoader( manager );
    loader.load( objpath, function ( object ) {
        object.traverse( function ( child ) {
            if ( child instanceof THREE.Mesh ) {
                child.material.map = texture;
            }
        } );
        object.position.y = 0;
        scene.add( object );
    }, onProgress, onError );
    renderer = new THREE.WebGLRenderer();
    renderer.setPixelRatio( window.devicePixelRatio );
    renderer.setSize( window.innerWidth, window.innerHeight );
    onWindowResize();
    container.appendChild( renderer.domElement );
    document.addEventListener( 'mousemove', onDocumentMouseMove, false );
    window.addEventListener( 'resize', onWindowResize, false );
}
function onWindowResize() {
    windowHalfX = window.innerWidth / 2;
    windowHalfY = window.innerHeight / 2;
    camera.aspect = window.innerWidth / window.innerHeight;
    camera.updateProjectionMatrix();
    renderer.setSize( window.innerWidth * 0.4, window.innerHeight * 0.4);
}
function onDocumentMouseMove( event ) {
    mouseX = ( event.clientX - windowHalfX ) / 2;
    mouseY = ( event.clientY - windowHalfY ) / 2;
}
function animate() {
    requestAnimationFrame( animate );
    render();
}
function render() {
    if (mouseX >= 0) {
        camera.position.x += ( -mouseX - camera.position.x ) * .05;
    } else {
        camera.position.x -= ( mouseX + camera.position.x ) * .05;
    }
    camera.position.y += ( mouseY - camera.position.y ) * .05;
    camera.lookAt( scene.position );
    renderer.render( scene, camera );
}
