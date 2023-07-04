// 작업공정까지 서버 API 통신 시 API호출이 너무 잦아질꺼 같아서 공종관리는 가리는 식으로 변경

function getSectionState(state) {
    switch(state) {
        case 0:
            return '작업없음';
            break;

        case 1:
            return '방수작업';
            break;

        case 2:
            return '토목작업';
            break;

        case 3:
            return '배관작업';
            break;

        case 4:
            return '비계작업';
            break;

        case 5:
            return '전기/제어작업';
            break;
            
        case 6:
            return '건축작업';
            break;
            
        case -99:
            return '정비작업';
            break;
            
        case 99:
            return '비활성';
            break;
    }
}