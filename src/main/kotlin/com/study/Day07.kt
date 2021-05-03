package com.study

fun Day07(){
    println("======================= Day 07 ============================= ")
    println("학습 요약: 컨트롤러 사용 , MVC 패턴\n" +
            "로그인 이후 수정/삭제 - 권한 체크" +
            "\n 멀티 게시판 : 게시판 이름, 게시판 코드 " +
            "\n 오브젝트는 복사가 안되지만, 클래스는 복사 가능" +
            "\n member01 = Member() , member02 = Member()" +
            "\n 클래스는 같은 틀이지만 다른 데이터로 사용 가능")
    println()
    println("1번 : 나가기 , 2번 : 해당 나의 프로그램 보기 ,")
    println("3번: 선생님 프로그램, 4번 : 추가 프로그램 ")
    println("============================================================== ")

    print("입력 : ")
    var chk = readLine()!!.trim().toInt()
    println()
    if(chk == 1){
        println("메인 페이지로 이동")
    }else if(chk == 2){
            Day06_teach()
    }else if(chk == 3){
//        Day00_teach()
    }else if(chk == 4){
//        Day00_addP()
    }else{
        println("메인 페이지로 이동")
    }

    println()
}


//fun Day00_P(){
//
//}
//fun Day00_teach(){
//
//}
//fun Day00_addP(){
//
//}


// 컨트롤러
fun systemController__exit(rq:Rq){
    println("프로그램을 종료합니다.")
}
object memberConntroller{
    fun logout(rq:Rq){
        if(loginCheck(login_info)){
            println("${login_info!!.login_name}님께서 로그아웃 하셨습니다. ")
            login_info = null
            if(login_info == null) println("알림) 정상적으로 로그아웃 되었습니다.")
            else println("로그아웃 에러 : 나중에 다시 시도해주세요")
        }
    }

    fun login(rq:Rq){
        println("=== 로그인 ===")
        print("로그인 아이디 : ")
        var join_id = readLineTrim()
        print("로그인 비밀번호 : ")
        var join_passwd = readLineTrim()

        var chk = memberRepository.userLogin(join_id, join_passwd)

        if(chk.login_id == "") println("로그인 실패 ! ID 값을 다시 확인해주세요.")
        else if(chk.login_id == "pass_fail") println("로그인 실패 ! 비밀 번호 값을 다시 확인해주세요.")
        else {
            println("! 로그인 성공! ${chk.login_name} 님 환영합니다.")
            login_info = chk
        }
    }

    fun join(rq: Rq){
        println("=== 회원 가입 ===")
        print("로그인 아이디 : ")
        var join_id = readLineTrim()

        print("로그인 비밀번호 : ")
        var join_passwd = readLineTrim()
        print("이름 : ")
        var join_name = readLineTrim()
        print("별명 : ")
        var join_nickname= readLineTrim()
        print("휴대전화 : ")
        var join_phone = readLineTrim()
        print("이메일 : ")
        var join_email = readLineTrim()

        var chk = memberRepository.userJoin(join_id, join_passwd, join_name, join_nickname, join_phone, join_email)
        if(chk == 0) println("회원가입이 완료 되었습니다.")
        else println("회원가입 실패")
    }
}


object articleController{
    fun write(rq:Rq){
        if(loginCheck(login_info)){
            print("제목 : ")
            val title = readLineTrim()
            print("내용 : ")
            val body = readLineTrim()
            print("게시판 선택 : ")
            val board_id = readLineTrim().toInt()
            if(!boardRepository.boardCheckInt(board_id)) {
                val id = articleRepository.addArticle(title, body, login_info!!.login_num, board_id)
                println("${id}번 게시물이 작성되었습니다.")
            }else{
                println("알림) 해당 게시판 코드가 존재하지 않습니다.")
            }
        }
    }

    fun list(rq:Rq){
        val page = rq.getIntParam("page", 1)
        val searchKeyword = rq.getStringParam("searchKeyword", "")
        val boardId = rq.getIntParam("boardId",0)
        val filteredArticles = articleRepository.getFilteredArticles(searchKeyword, page,boardId, 10)

        println("번호 /       작성날짜       /       갱신날짜        /   제목   / 작성자 / 게시판")

        for (article in filteredArticles) {
            var boardName:String =""
            for(i in boardRepository.boards){
                if(i.boardId == article.board_id) boardName = i.boardName
            }
            println("${article.id} / ${article.regDate} / ${article.updateDate} / ${article.title} / ${memberRepository.getMember(article.member_num).login_id} / ${boardName} ")
        }
    }

    fun modify(rq:Rq):Int{
        if(loginCheck(login_info)) {
            val id = rq.getIntParam("id", 0)

            if (id == 0) {
                println("id를 입력해주세요.")
                return 0
            }

            val article = articleRepository.getArticleById(id)

            if (article == null) {
                println("${id}번 게시물은 존재하지 않습니다.")
                return 0
            }
            if(article.member_num == login_info!!.login_num) {
                print("${id}번 게시물 새 제목 : ")
                val title = readLineTrim()
                print("${id}번 게시물 새 내용 : ")
                val body = readLineTrim()

                articleRepository.modifyArticle(id, title, body)

                println("${id}번 게시물이 수정되었습니다.")
                return 0
            }else {
                println("알림) 수정은 본인 글만 가능합니다.")
                return 0
            }
        }else{
            return 0
        }

    }

    fun delete(rq:Rq){
        if(loginCheck(login_info)) {
            val id = rq.getIntParam("id", 0)

            if (id == 0) {
                println("id를 입력해주세요.")
            }else {
                val article = articleRepository.getArticleById(id)

                if (article == null) {
                    println("${id}번 게시물은 존재하지 않습니다.")
                }else {
                    if (article.member_num == login_info!!.login_num) {
                        articleRepository.deleteArticle(article)
                        println("알림) ${id}번 글을 정상적으로 삭제했습니다.")
                    } else {
                        println("알림) 본인이 작성한 글만 삭제가 가능합니다.")
                    }
                }
            }
        }
    }


    fun detail(rq:Rq){
        val id = rq.getIntParam("id", 0)

        if (id == 0) {
            println("id를 입력해주세요.")
        }else {

            val article = articleRepository.getArticleById(id)

            if (article == null) {
                println("${id}번 게시물은 존재하지 않습니다.")
            }else {

                println("번호 : ${article.id}")
                println("작성날짜 : ${article.regDate}")
                println("갱신날짜 : ${article.updateDate}")
                println("제목 : ${article.title}")
                println("내용 : ${article.body}")
            }
        }
    }
}


class BoardController {
    fun add(rq: Rq) {
        print("이름 : ")
        var boardName = readLineTrim()
        print("코드 : ")
        var boardId = readLineTrim().toInt()

        var chk = boardRepository.boardCheck(boardId, boardName)

        if(chk){
            boardRepository.boardAdd(boardId, boardName)
            println("${boardName} 게시물이 생성 되었습니다.")

        }else{
            println("알림) 이미 만들어진 중복 값이 있습니다.")
        }
    }

    fun list(rq: Rq) {
        println("게시판 코드 / 이름")
        for (i in boardRepository.boards){
            println("${i.boardId}  /  ${i.boardName}")
        }
    }

}

