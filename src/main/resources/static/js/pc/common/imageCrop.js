// Global Variables
let imageType;

$(document).ready(() => {
    /*
    *   crop image using cropper.js
    */
    let cropper;

    $("#filePhoto").change(event => {
        // jquery object 는 file property를 가지고 있지 않음
        // 그러므로 event target에서 file property를 가져와야함

        let input = $(event.target);


        if(input[0].files && input[0].files[0]) {

            let reader = new FileReader();

            // image file를 성공적으로 읽어왔을 시 아래 onload handler 실행
            reader.onload = (event) => {

                $("#imageUploadButton").removeAttr("disabled");
                let image = document.getElementById("imagePreview"); // 수정할 곳

                image.src = event.target.result;

                if(cropper !== undefined) {
                    // Destroy the cropper and remove the instance from the image.
                    // destroy method is a cropper method
                    cropper.destroy();

                }

                cropper = new Cropper(image , {
                    aspectRatio : 1/1,
                    background : false,
                    zoomOnWheel : false
                });

            }
            /*  The readAsDetailURL method is used to read the contents of
            the specified Blob or File , when the read operation is finished,
            the readyState becomes DONE , and the loadend is triggered. At that time,
            the result attribute contains the data as a data: URL representing the file's data as a base64 encoded string.
            */
            reader.readAsDataURL(input[0].files[0]);
        }
    });

    $("#imageUploadButton").on('click' , ()=> {
        let canvas = cropper.getCroppedCanvas();
        // Upload cropped image to server if the browser supports `HTMLCanvasElement.toBlob`.
        // The default value for the second parameter of `toBlob` is 'image/png', change it if necessary.

        if(canvas == null || canvas == undefined ) {
            alert('Could not upload Image. Make sure it is an image file.');
            return;
        }
        const imageType = $("#imageUploadButton").data("imagetype");

        canvas.toBlob( blob => {
            console.log(blob);

            let originFilename = generateRandomImageFileName(10 , "png");
            let formData = new FormData(); // blob을 서버로 전송할 때 formdata 사용


            formData.append('blob' , blob , originFilename);
            formData.append("imageType" , imageType);

            $.ajax({
                url : `/images/upload`,
                type : 'POST',
                data : formData,
                processData: false,
                contentType: false,
                success() {
                    console.log('Upload success');
                },
                error() {
                console.log('Upload error');
                }
           });
        });
    });
});

// 랜덤 문자열 생성 함수
const generateRandomImageFileName = (num , extension) => {
  const characters ='ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz';
  let result = "";
  const charactersLength = characters.length;
  for(let i = 0 ; i < num ; i++) {
    result += characters.charAt(Math.floor(Math.random() * charactersLength));
  }

  result += "." + extension;

  return result;
}