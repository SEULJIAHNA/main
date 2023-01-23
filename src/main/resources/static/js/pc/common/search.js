/*
    검색 기능 구현 (main searchBox)
*/

const search = {
    // 실시간 검색 중
    searching : function() {
        $("#search-keyword").keyup(event => {
            // search box에 keyword 입력시 setTimeout 함수 실행 취소
            clearTimeout(timer);
            let searchKeywordBox = $(event.target);
            let value = searchKeywordBox.val();
            console.log(value);

            // 1초 이상 searchbox에 keyword 입력이 없을 시 timer 함수 실행 (실시간 검색 키워드)
            timer = setTimeout(() => {
                searchKeywordBox.val("");
            }, 1000);
        });
    }
}


let timer;

// html loading 후 js 실행
$(document).ready(() => {
    search.searching();
});
