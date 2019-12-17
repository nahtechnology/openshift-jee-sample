var spacesEndpoint = new AWS.Endpoint('nyc3.digitaloceanspaces.com');
var imagenes = [];
var parametros = [];
// var contenedorimagen = document.querySelector('.contenedor-img');
var imagenDocumento = document.querySelectorAll(".contenedor-img img");
var ruta = "imagenes_glosario/";
var imagen = document.createElement('img');


document.addEventListener('DOMContentLoaded', function (evt) {
    imagenDocumento.forEach(function (imagen) {
        var contexto = imagen.dataset.llave;
        imagenes.push(contexto);
    });
    var s3 = new AWS.S3({
        endpoint: spacesEndpoint,
        accessKeyId: 'GEXURWPHJX37JPR2VGWY',
        secretAccessKey: 'EOvb5YkVHMViTG12aEvSfsRz5clDgpXGJdlq2UrpHHs'
        //params: {Bucket: "tecolotl-multimedia"}
    });

    imagenes.forEach(function (imagen,index) {
        parametros[index] = {
            Bucket: "tecolotl-multimedia",
            Key: imagen
        };
        s3.getObject(parametros[index], function(err, data) {
            if (err) {
                console.log(err, err.stack);
                imagenDocumento[index].src = "../resources/img/vacio.svg";
                imagenDocumento[index].classList.remove("cargando");
                imagenDocumento[index].classList.add("glosario-img");
            }
            else {
                console.log(data);
                var direccion = new Blob([data.Body], {type: data.ContentType});
                console.log(direccion);
                imagenDocumento[index].src = URL.createObjectURL(new Blob([data.Body], {type: data.ContentType}));
                imagenDocumento[index].classList.remove("cargando");
                imagenDocumento[index].classList.add("glosario-img");
            }
        });
    });
});







