package com.study
// 멤버 저장 해야됌
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.io.File

var articlesLastId_02 = 0

val articles_02 = mutableListOf<Article_02>()

var articleTest_02: Article_02? = null
var now_member:Member_03? = null


fun Day09() {
    println("======================= Day 09 ============================= ")
    println("학습 요약: 게시판 복습 (처음부터 다시 제작) - ")
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
        Day09_Exam()
    }else if(chk == 3){
//        Day00_teach()
    }else if(chk == 4){
//        Day00_addP()
    }else{
        println("메인 페이지로 이동")
    }

    println()
}

fun Day09_Exam(){
    println("=== 게시판 프로그램 시작 ===")
    try {
        dataRepository.loadingData()
    }catch (e:Exception){
        println("아직 작성된 글이 없습니다.")
    }
    while(true){
        print("명령어 입력) ")
        var command = readLineTrim()
        var rq = Rq_02(command)
        when(rq.actionPath) {
            "/system/exit" ->{
                println("시스템 종료")
                break;
            }
            "/article/write" ->{
                if(now_member == null) println("로그인 이후 사용 가능한 명령어 입니다.")
                else {
                    print("제목 : ")
                    var title = readLineTrim()
                    print("내용 : ")
                    var contents = readLineTrim()
                    print("게시판 : ")
                    var board_id = readLineTrim().toInt()
                    articleRepository_02.addArticle(title, contents, now_member!!.login_num,board_id)
                    println("${id} 번째 글이 작성 되었습니다.")
                    dataRepository.dataStored()
                }
            }
            "/article/list" ->{
                var page = rq.getIntParam("page", 0)
                var keyWords = rq.getStringParam("searchKey", "")
                articleRepository_02.getFilteringList(page , keyWords, 10)
            }
            "/article/modify" -> {
                if(now_member == null){
                    println("로그인 이후 사용 가능한 명령어 입니다.")
                }else{
                    val id = rq.getIntParam("id", 0)
                    if (id == 0) {
                        println("id를 입력해주세요.")
                    }
                    val article = articleRepository_02.getArticleById(id)
                    if (article == null) {
                        println("${id}번 게시물은 존재하지 않습니다.")
                    }
                    if(article!!.member_num == now_member!!.login_num) {
                        print("${id}번 게시물 새 제목 : ")
                        val title = readLineTrim()
                        print("${id}번 게시물 새 내용 : ")
                        val body = readLineTrim()
                        articleRepository.modifyArticle(id, title, body)
                        println("${id}번 게시물이 수정되었습니다.")
                        dataRepository.dataStored()
                    }else {
                        println("알림) 수정은 본인 글만 가능합니다.")
                    }
                }
            }
            "/article/delete"->{
                if(now_member == null){
                    println("로그인 이후 사용 가능한 명령어 입니다.")
                }else {
                    val id = rq.getIntParam("id", 0)
                    if (id == 0) {
                        println("id를 입력해주세요.")
                    } else {
                        val article = articleRepository_02.getArticleById(id)
                        if (article == null) {
                            println("${id}번 게시물은 존재하지 않습니다.")
                        } else {
                            if (article!!.member_num == now_member!!.login_num) {
                                articleRepository_02.deleteArticle(article)
                                println("글이 삭제되었습니다.")
                                dataRepository.dataStored()
                            } else {
                                println("작성자만 삭제가 가능합니다.")
                            }
                        }
                    }
                }
            }
            "/member/join" -> {
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
                var chk = memberRepository_02.userJoin(join_id, join_passwd, join_name, join_nickname, join_phone, join_email)
                if(chk == 0) println("회원가입이 완료 되었습니다.")
                else println("회원가입 실패")
            }
            "/member/login" ->{
                print("아이디 : ")
                var id = readLineTrim()
                print("비밀번호 : ")
                var passwd = readLineTrim()
                var ck = memberRepository_02.userLogin(id,passwd)
                if(ck == 1) println("비밀번호를 확인하세요.")
                else if(ck == 0) println("아이디를 다시 확인하세요.")
                else if(ck==2){
                    println("로그인 성공!")
                }
            }
            "/member/logout" ->{
                if(now_member == null){
                    println("로그인 이후 사용 가능한 명령어 입니다.")
                }else{
                    println("로그아웃 되었습니다.")
                    now_member = null
                }
            }
            "/board/make" ->{
                if(now_member == null) println("로그인 이후 사용 가능한 명령어 입니다.")
                else BoardRepository.add()
            }
            "/board/list" ->{
                BoardRepository.list()
            }
            else ->{
                println("${command}는 없는 명령어 입니다.")
            }
        }
    }
}


var id = 0

object dataRepository{

    fun loadingData(){
        var mapper = jacksonObjectMapper()
        val articles = mapper.readValue<ArrayList<Article_02>>(File("C:\\Users\\yungi\\IdeaProjects\\Study\\src\\main\\assets\\article.json"))
        for(article in articles) articles_02.add(article)

        val boards = mapper.readValue<ArrayList<Board_02>>(
            File("C:\\Users\\yungi\\IdeaProjects\\Study\\src\\main\\assets\\board.json")
        )
        for(board in boards) BoardRepository.boards.add(board)

        var members = mapper.readValue<ArrayList<Member_02>>(
            File("C:\\Users\\yungi\\IdeaProjects\\Study\\src\\main\\assets\\member.json")
        )
//        for(member in members) memberRepository_02.setMember
    }

    fun dataStored(){
        var mapper = jacksonObjectMapper()
        mapper.writerWithDefaultPrettyPrinter().writeValue(
            File("C:\\Users\\yungi\\IdeaProjects\\Study\\src\\main\\assets\\article.json"),
            articles_02)

        mapper.writerWithDefaultPrettyPrinter().writeValue(
            File("C:\\Users\\yungi\\IdeaProjects\\Study\\src\\main\\assets\\board.json"),
            BoardRepository.boards)
    }
}

object articleRepository_02 {

    fun addArticle(title: String, contents: String, member_id:Int, board_id: Int){
        id++
        var regDate = Util_02.getNowDateStr()
        var updateDate = ""
        articles_02.add(Article_02(id,regDate,updateDate,title,contents, member_id,board_id))
    }

    fun modifyArticle(id: Int, title: String, body: String) {
        val article = articleRepository_02.getArticleById(id)!!

        article.title = title
        article.body = body
        article.updateDate = Util.getNowDateStr()
    }

    fun deleteArticle(article: Article_02) {
        articles_02.remove(article)
    }

    fun getArticleById(id: Int): Article_02? {
        for (article in articles_02) {
            if (article.id == id) {
                return article
            }
        }

        return null
    }

    fun getFilteringList(page:Int , keyWords:String, filteringId:Int){
        // 1 page = 최근 데이터
        if(page == 0){
            getFilteringPage(articles_02, filteringId, page)
        }else if(keyWords == "") {
            var totalPage:Int = articles_02.size/filteringId
            var startNum = articles_02.lastIndex-filteringId*(page-1)
            var endNum = startNum - filteringId-1
            if(endNum < 0) {
                endNum = 0
            }

            var PageArticle = mutableListOf<Article_02>()
            try {
                for (i in startNum downTo endNum) {
                    PageArticle.add(articles_02[i])
                }
                getFilteringPage(articles_02, filteringId, page)
            }catch(e:Exception){
                println("Page 수를 다시 확인해주세요.")
            }

        }else{
            var searchList = mutableListOf<Article_02>()
            for(article in articles_02){
                if(article.title.contains(keyWords)) searchList.add(article)
            }

            getFilteringPage(searchList, filteringId, page)
        }
    }

    fun getFilteringPage(article:List<Article_02>, filteringId: Int, page: Int){
        var totalPage:Int = article.size/filteringId
        var startNum = article.size-filteringId*(page-1)
        var endNum = startNum - 10
        println("번호 /       작성날짜       /       갱신날짜        /   제목   / 작성자 / 게시판")
        for (i in article) {
            var updateDate = ""
            if(i.updateDate == "") updateDate ="         없음         "
            else updateDate = i.updateDate
            println("${i.id} / ${i.regDate} / ${updateDate} / ${i.title} / ${memberRepository_02.getMember(i.member_num).login_id} / ${BoardRepository.getBoardName(i.board_id)}")
        }
    }



}

object memberRepository_02{
    private val members = mutableListOf<Member_03>()
    private var mem_num = 0
    fun getMember(num:Int):Member_03{
        return  members[num]
    }


    fun userJoin(join_id:String, join_passwd:String,join_name:String,join_nickname:String,join_phone:String, join_email:String ):Int{
        var num = mem_num
        try {
            members.add(Member_03(num,join_id, join_passwd, join_name, join_nickname, join_phone, join_email))
            mem_num += 1
            return 0
        }catch (e: java.lang.Exception) {
            return 1
        }
    }

    fun userLogin(id: String,passwd:String):Int{
        var ck:Int = 0
        for (member in members) {
            if (id == member.login_id){
                ck = 1
                if(passwd == member.login_passwd){
                    ck = 2
                    now_member = member
                }
            }
        }

        return ck
    }

//    fun makeTestMember(){
//        for(i in 0 .. 10){
//            var id = "admin$i"
//            var pass = "admin"
//            var name = "admin$i"
//            var tell = "비공개"
//            var email = "admin@admin.com"
//
//            memberRepository_02.userJoin(id, pass, name, tell, "관리자", email)
//        }
//    }
}


object BoardRepository {
    var boards = mutableListOf<Board_02>()

    fun getBoardName(board_id: Int):String{
        for (i in boards){
            if(i.boardId == board_id){
                return i.boardName
            }
        }
        return ""
    }

    fun add() {
        print("이름 : ")
        var boardName:String = readLineTrim()
        print("코드 : ")
        var boardId = readLineTrim().toInt()

        var chk = BoardRepository.boardCheck(boardId, boardName)

        if(chk){
            boards.add(Board_02(boardId, boardName))
            println("${boardName} 게시물이 생성 되었습니다.")
            dataRepository.dataStored()

        }else{
            println("알림) 이미 만들어진 중복 값이 있습니다.")
        }
    }

    fun list() {
        println("게시판 코드 / 이름")
        for (i in boards){
            println("${i.boardId}  /  ${i.boardName}")
        }
    }

    fun boardCheck(boardId: Int,boardName: String):Boolean{
        for(i in boards) {
            if(i.boardId == boardId) return false
            if(i.boardName == boardName) return false
        }
        return true
    }


}


class Rq_02(command: String) {
    // 데이터 예시
    // 전체 URL : /artile/detail?id=1
    // actionPath : /artile/detail
    val actionPath: String

    // 데이터 예시
    // 전체 URL : /artile/detail?id=1&title=안녕
    // paramMap : {id:"1", title:"안녕"}
    private val paramMap: Map<String, String>

    // 객체 생성시 들어온 command 를 ?를 기준으로 나눈 후 추가 연산을 통해 actionPath와 paramMap의 초기화한다.
    // init은 객체 생성시 자동으로 딱 1번 실행된다.
    init {
        // ?를 기준으로 둘로 나눈다.
        val commandBits = command.split("?", limit = 2)

        // 앞부분은 actionPath
        actionPath = commandBits[0].trim()

        // 뒷부분이 있다면
        val queryStr = if (commandBits.lastIndex == 1 && commandBits[1].isNotEmpty()) {
            commandBits[1].trim()
        } else {
            ""
        }

        paramMap = if (queryStr.isEmpty()) {
            mapOf()
        } else {
            val paramMapTemp = mutableMapOf<String, String>()

            val queryStrBits = queryStr.split("&")

            for (queryStrBit in queryStrBits) {
                val queryStrBitBits = queryStrBit.split("=", limit = 2)
                val paramName = queryStrBitBits[0]
                val paramValue = if (queryStrBitBits.lastIndex == 1 && queryStrBitBits[1].isNotEmpty()) {
                    queryStrBitBits[1].trim()
                } else {
                    ""
                }

                if (paramValue.isNotEmpty()) {
                    paramMapTemp[paramName] = paramValue
                }
            }

            paramMapTemp.toMap()
        }
    }

    fun getStringParam(name: String, default: String): String {
        return paramMap[name] ?: default
    }

    fun getIntParam(name: String, default: Int): Int {
        return if (paramMap[name] != null) {
            try {
                paramMap[name]!!.toInt()
            } catch (e: NumberFormatException) {
                default
            }
        } else {
            default
        }
    }
}


data class Article_02(
    val id: Int,
    val regDate: String,
    var updateDate: String,
    var title: String,
    var body: String,
    var member_num:Int,
    var board_id:Int
)
/* 게시물 관련 끝 */

/* 유틸관련 시작 */

object Util_02 {
    fun getNowDateStr(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

        return dateFormat.format(System.currentTimeMillis())
    }
}

data class Member_03(
    var login_num:Int,
    var login_id:String ,
    var login_passwd:String,
    var login_name:String,
    var login_nickname:String,
    var login_tell:String,
    var login_email:String
){}
/* 유틸관련 끝 */

data class Board_02(
    var boardId:Int,
    var boardName:String
){}