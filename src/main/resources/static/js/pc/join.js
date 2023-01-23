// 비동기 방식의 중복확인 구현
let timer2;
let timerOfPassword;

// 닉네임 , 이메일 중복확인 변수

let checkList = new Object({
    "isDuplicatedEmail" : false,
    "isDuplicatedNickname" : false,
    "isValidateOfPassword" : false,
    "isCheckPassword" : false,
    "isValidateOfEmail" : false,
    "consentToCollectionOfPersonalInfo" : false,
    "isValidatedNickname" : false
});

$(document).ready(()=> {

    $("#nickname, #email").on('keyup' , function(event) {

        // tab키 입력시 이벤트 함수 실행 x
        if(event.keyCode == 9) {
            return;
        }

        // keydown 이벤트 발생 했을 시 1초 뒤 서버에 중복확인 비동기 요청 보냄
        // 1초 경과 전 다시 keydown 입력 시 요청 취소
        clearTimeout(timer2);

        let type = event.target.id;
        let className = $("#" + type);
        const resultContainer = $(className);
        let result_input_container = resultContainer.parent(".input-container");
        result_input_container.removeAttr("data-error data-success");

        // 1초 이상 추가적인 입력이 없을 경우 서버에 중복확인 요청
        timer2 = setTimeout(() => {

            value = event.target.value;

            if(value == "") {
                result_input_container.removeAttr("data-error");

                if( type == "email") {
                   checkList.isValidateOfEmail = false;
                } else {
                    checkList.isDuplicatedNickname = false;
                }
            }

            else {

                /*nickname 유효성 검사*/
                if(type === "nickname") {
                    checkList.isValidatedNickname = checkNickname(value);
                    if(!checkList.isValidatedNickname) {

                        result_input_container.attr("data-error" , "사용 할 수 없는 닉네임입니다.");

                        return;
                    }
                    // 데이터 유효성 입증 시 data-error 속성 제거 (css 제거)
                    result_input_container.removeAttr("data-error");
                }

                checkForDuplicate(value , type);

            }

        } , 100);

    });

    /*
        password validation
    */
    $("#password").on("keyup" , function(event) {

        if(event.keyCode == 9) {
            return;
        }

        let value = event.target.value;
        clearTimeout(timerOfPassword);
        const re_password_container = $("#re-password");
        const password_container = $("#password");
        let password_input_container = password_container.parent(".input-container");
        let re_password_input_container = re_password_container.parent(".input-container");
        re_password_input_container.removeAttr("data-success data-error");
        password_input_container.removeAttr("data-success data-error");


        // re-password 검증 후 password 값을 변경 할 경우 다시 re-password 값과 일치하는지 검증
        if($("#re-password")[0].value != "") {

            if(checkRePassword(value ,$("#re-password")[0].value)) {
                checkList.isCheckPassword = true;
                re_password_input_container.attr("data-success" , "비밀번호가 일치합니다.");

            } else {
                checkList.isCheckPassword = false;
                re_password_input_container.attr("data-error" , "비밀번호가 일치하지 않습니다.");

            }
        }

        timerOfPassword = setTimeout(() => {

           if(value !== "") {
                if(checkPassword(value)) {
                    checkList.isValidateOfPassword = true;
                    password_input_container.attr("data-success" , "사용 가능한 패스워드 형식입니다.");
                } else {
                    checkList.isValidateOfPassword = false;
                    password_input_container.attr("data-error" , "사용 불가능한 패스워드 형식입니다.");
                }
            }
        } , 100);


        /*회원가입 수행 로직 (서버에 userJoinDto 보냄) */
    });

    // 비밀번호 재입력 값과 비밀번호 값이 일치하는지 확인
    $("#re-password").on("keyup", function(event) {

        if(event.keyCode == 9) {
            return;
        }

        clearTimeout(timerOfPassword);

        const re_password_container = $("#re-password");
        let re_password_input_container = re_password_container.parent(".input-container");
        re_password_input_container.removeAttr("data-error data-success");

        let inputPassword = document.getElementById("password").value;
        let inputRePassword = event.target.value;

        if(inputRePassword == "") {
            re_password_input_container.removeAttr("data-error data-success");
            return;
        }

        timerOfPassword = setTimeout(() => {
            if(re_password_container.value !== ""){
                if(checkRePassword(inputPassword , inputRePassword)) {
                    checkList.isCheckPassword = true;
                    re_password_input_container.attr("data-success" , "비밀번호가 일치합니다.");

                } else {
                    checkList.isCheckPassword = false;
                    re_password_input_container.attr("data-error" , "비밀번호가 일치하지 않습니다.");
                }
            }

        }, 300);
    });

    // whether you agree to personal information
    $("#agreement").on("click" , function() {
        checkList.consentToCollectionOfPersonalInfo = $(this).is(":checked") ? true : false;
    });

    /*
    *   submit button click 시 발생 event control
    *   비동기 (ajax) 방식으로 서버에 회원가입 데이터 전당
    */

     window.addEventListener('load', () => {
          const forms = document.getElementsByClassName('validation-form');

          Array.prototype.filter.call(forms, (form) => {
            form.addEventListener('submit', function (event) {
                event.preventDefault();
                event.stopPropagation();

                const isValidated = validateCheckListValue(checkList); // 회원가입 field 유효성 검사

                if(isValidated === true) { // isValidated 값이 true 면 모든 유효성 검사 완료 , false 유효하지 않은 field 존재
                    const formData = new FormData(form); // convert to form object to formData
                    // to convert the form data into a valid js object
                    let formDataObject = Object.fromEntries(formData.entries());

                    // userDto field 중 nested class인 address 클래스에 넣기 위한 object 생성
                    let address = new Object();

                    // (ex address.roradAddress -> roadAddress)
                    if(formDataObject.hasOwnProperty("address.roadAddress")) {
                        address["roadAddress"] = formDataObject["address.roadAddress"];
                        delete formDataObject["address.roadAddress"];
                    };

                    if(formDataObject.hasOwnProperty("address.jibunAddress")) {
                        address["jibunAddress"] = formDataObject["address.jibunAddress"];
                        delete formDataObject["address.jibunAddress"];
                    };

                    if(formDataObject.hasOwnProperty("address.postCode") ) {
                        address["postCode"] = formDataObject["address.postCode"];
                        delete formDataObject["address.postCode"];
                    };

                    if(formDataObject.hasOwnProperty("address.extraAddress") ) {
                        address["extraAddress"] = formDataObject["extraAddress"];
                        delete formDataObject["address.extraAddress"];
                    };

                    if(formDataObject.hasOwnProperty("address.detailAddress") && formDataObject["address.detailAddress"] != null) {
                        address["detailAddress"] = formDataObject["address.detailAddress"];
                        delete formDataObject["address.detailAddress"];
                    };

                    formDataObject['address'] = address;

                    console.log(formDataObject);
                    // send converted formDataObject to server (ajax , json format)
                    requestUserDtoToServer(formDataObject);
                }
            })
        }, false);
      });
});




// 중복확인 (parameter =  value : 중복확인할 값 , type : )
function checkForDuplicate(value , type) {

    let resultMessage; // 중복체킹 결과 메세지 return
    const email_container = $("#email");
    const nickname_container = $("#nickname");
    let nickname_input_container = nickname_container.parent(".input-container");
    let email_input_container = email_container.parent(".input-container");


    let url = "/user-" + type + "s/" + value + "/exists";

    $.ajax({
        url : url,
        type : "GET",
        async : false, // resultMessage 값을 담기 위해 비동기 형식으로 처리
        success : function(result) {

            if(!result) {  // result : true : 중복 존재 , false 중복 x

                if(type == "email") {
                    if(!checkEmail(value)) { // email 입력일 경우 email 형식이 맞는지 확인;
                        checkList.isValidateOfEmail = false;
                        checkList.isDuplicatedEmail = false;

                        email_input_container.attr("data-error" , "이메일 형식이 올바르지 않습니다.");


                        return;
                    }


                    email_input_container.attr("data-success" , "사용 가능한 이메일입니다.");
                    checkList.isDuplicatedEmail = true;
                    checkList.isValidateOfEmail = true;

                    return;
                }

                // type : nickname 일 경우
                nickname_input_container.attr("data-success" , "사용 가능한 닉네임 입니다.");
                checkList.isDuplicatedNickname = true;

            }
            else { // 중복 존재할 경우
                if(type == "nickname") {
                    checkList.isDuplicatedNickname = false;
                    nickname_input_container.attr("data-error" , "이미 사용중인 닉네임 입니다.");

                } else {
                    checkList.isDuplicatedEmail = false;
                    email_input_container.attr("data-error" , "이미 사용중인 이메일 입니다.");

                }

            }
        },

        error : function(request , status , error) {
            console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
        }
    });
    return;
}


// 이메일 유효성 검사 함수
function checkEmail(email) {
    const reg_email = /^([0-9a-zA-Z_\.-]+)@([0-9a-zA-Z_-]+)(\.[0-9a-zA-Z_-]+){1,2}$/;

    if(!reg_email.test(email)) {
        return false;
    } else {
        return true;
    }
}

/*닉네임 유효성 검사*/
function checkNickname(nickname) {
    const NICKNAME_RULE = /^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$/; // 닉네임은 특수문자를 제외한 2~10자리

    return NICKNAME_RULE.test(nickname);

}


// 비밀번호 유효성 검사 함수 (특수문자 1개 , 숫자, 1개, 문자 1개 가 무조건 최소로 들어가야하고 최소 8자리 에서 16자리까지 허용)
function checkPassword(password) {
    const PWD_RULE = /^(?=.*[a-zA-Z])((?=.*\d)(?=.*\W)).{8,16}$/;

    return PWD_RULE.test(password);

}

// 비밀번호와 비밀번로 재입력 값이 같은지 확인하는 함수
function checkRePassword(password , re_password) {
    if(password == re_password) {
        return true;
    } else {
        return false;
    }

}

/*userRequestDto to server using ajax*/
function requestUserDtoToServer(formDataObject) {

    console.log(JSON.stringify(formDataObject));

    $.ajax({
        type : "POST",
        url: "/new",
        data : JSON.stringify(formDataObject), // http body에 넣을 데이터
        contentType:"application/json; charset=utf-8" , // body data type : json
    }).done(function(result , textStatus , xhr ) {
        // 응답 성공 시 로직
        window.location = xhr.getResponseHeader("Location");
    }).fail(function(error){
        // 응담 요청 실패 시 로직
        console.log(error);

    });
}


/*checkList object에 null / undefined / NaN 값 존재하는지 여부 확인*/
/* @Param : object (checkList) */
/* @return : boolean -> 존재하면 true , 모두 null 값이 아니면 false */
function validateCheckListValue(checkList) {
    if(checkList.constructor === Object && Object.keys(checkList).length !== 0 ) {
        for (const checkListKey in checkList) {
            // checkList 값 중 null or NAN or 빈 값이 있을 경우 break 후 true 리턴
            let check = checkList[checkListKey];
            if(check == NaN || check == null || typeof check !== 'boolean' || check === false) {
                return false;
            }
        }

        return true;
    }

    return false;
}



//// 개인정보 동의 체크 박스 체크 여부 확인
//function is_checked() {
//    const checkBox = document.getElementById("agreement");
//
//    const is_checked = checkBox.checked;
//
//    // 결과 출력
//    return is_checked;
//}

