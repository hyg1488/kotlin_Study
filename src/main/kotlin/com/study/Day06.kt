package com.study
import com.google.gson.Gson


import sun.font.TrueTypeFont
import java.io.FileReader
import java.io.IOException
import java.lang.Exception
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.text.SimpleDateFormat
import java.util.Random

val pathArticle = "C:\\Users\\yungi\\IdeaProjects\\Study\\src\\main\\assets\\article.json"
val PathMember = "C:\\Users\\yungi\\IdeaProjects\\Study\\src\\main\\assets\\member.json"
val pathBoard = "C:\\Users\\yungi\\IdeaProjects\\Study\\src\\main\\assets\\board.json"

var login_info: Member? = null
var articleString = ""
var memberString = ""

fun Day06(){
    println("---- Day06 ----")
    println("학습 요약: 회원가입, 로그인, 로그인후 글쓰기, 작성자 리스트에 추가" +
            "\n ! Main = day06 ,  Controller = day07")
    println()
    println("1번 : 나가기 , 2번 : 해당 나의 프로그램 보기 ")
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


fun Day06_teach(){
    println("== SIMPLE SSG 시작 ==")
    memberRepository.makeTestMember()
    boardRepository.makeTestBoard()
    articleRepository.loaddingArticle()
    var board:BoardController = BoardController()

    while (true) {
        println()
        if(login_info != null){
            println("!! 정보 : ${login_info!!.login_name}님이 현재 접속 중입니다 !!")
        }
        print("명령어) ")
        val command = readLineTrim()

        val rq = Rq(command)

        when (rq.actionPath) {

            "/system/exit" -> {
                systemController__exit(rq)
                break
            }
            "/article/list" -> {
                articleController.list(rq)
            }
            "/article/detail" -> {
                articleController.detail(rq)
            }
            "/article/modify" -> {
                articleController.modify(rq)
            }
            "/article/delete" -> {
                articleController.delete(rq)
            }
            "/article/write" -> {
                articleController.write(rq)
            }

            "/member/join" ->{
                memberConntroller.join(rq)
            }

            "/member/login" ->{
                memberConntroller.login(rq)
            }
            "/member/logout" ->{
                memberConntroller.logout(rq)
            }
            "/board/add"->{
                board.add(rq)
            }
            "/board/list" ->{
                board.list(rq)
            }
            else -> {
                println("${command}는 존재하지 않는 명령어입니다.")
            }



        }
    }

    println("== SIMPLE SSG 끝 ==")
}


fun loginCheck(login_info:Member?):Boolean{
    var chk:Boolean = false
    if(login_info ==null){
        println("알림) 해당 명령어는 로그인 후 이용해주세요.")
    }else {
        chk = true
    }

    return chk
}

// 회원 DTO
data class Member(
    var login_num:Int,
    var login_id:String ,
    var login_passwd:String,
    var login_name:String,
    var login_nickname:String,
    var login_tell:String,
    var login_email:String
){}

// Rq는 UserRequest의 줄임말이다.
// Request 라고 하지 않은 이유는, 이미 선점되어 있는 클래스명 이기 때문이다.
class Rq(command: String) {
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


object articleRepository {
    private val articles = mutableListOf<Article>()
    private var lastId = 0

    fun deleteArticle(article: Article) {
        articles.remove(article)
    }

    fun getArticleById(id: Int): Article? {
        for (article in articles) {
            if (article.id == id) {
                return article
            }
        }

        return null
    }

    fun addArticle(title: String, body: String, user_num:Int,board_id: Int):Int {
        val id = ++lastId
        val regDate = Util.getNowDateStr()
        val updateDate = Util.getNowDateStr()
        articles.add(Article(id, regDate, updateDate, title, body, user_num,board_id))
        if(articleString == "") {
            articleString += """ {
                                "id" : "${id}",
                                "regDate" : "${regDate}",
                                "updateDate" : "${updateDate}",
                                "title" : "${title}",
                                "body" : "${body}",
                                "user_num" : "${user_num}",
                                "board_id" : "${board_id}"
                            }""".trimIndent()
        }else{
            articleString += """&{
                                "id" : "${id}",
                                "regDate" : "${regDate}",
                                "updateDate" : "${updateDate}",
                                "title" : "${title}",
                                "body" : "${body}",
                                "user_num" : "${user_num}",
                                "board_id" : "${board_id}"
                            }""".trimIndent()
        }
        
        try{
            Files.write(Paths.get(pathArticle), articleString.toByteArray(), StandardOpenOption.CREATE)
        }catch (e:IOException){
            println("저장 에러")
        }
        
        return id
    }

    fun loaddingArticle(){
        val read = FileReader(pathArticle)
        val articleStringList = read.readText().split("&")
        for (i in articleStringList.indices){
            if(i == 0){
                articleString = articleStringList[0]
            }else{
                articleString += "&"+articleStringList[i]
            }

            try {
                val articleLoad = Gson().fromJson(articleStringList[i], Article::class.java)
                articleRepository.articles.add(articleLoad)

            }catch (e:Exception){
            }
            if(i != 0) {
                if (i == articleStringList.size - 1) {
                    lastId = articles[i].id
                }
            }
        }

    }

    fun makeTestArticles() {
        val random = Random()
        for (id in 1..100) {
            addArticle("제목_$id", "내용_$id", random.nextInt(11), random.nextInt(2))
        }
    }

    fun modifyArticle(id: Int, title: String, body: String) {
        val article = getArticleById(id)!!

        article.title = title
        article.body = body
        article.updateDate = Util.getNowDateStr()
    }

    fun getFilteredArticles(searchKeyword: String, page: Int,board_id: Int, itemsCountInAPage: Int): List<Article> {
        val filtered1Articles = getSearchKeywordFilteredArticles(articles, searchKeyword,board_id)
        val filtered2Articles = getPageFilteredArticles(filtered1Articles, page, itemsCountInAPage)

        return filtered2Articles
    }

    private fun getSearchKeywordFilteredArticles(articles: List<Article>, searchKeyword: String, board_id: Int): List<Article> {
        val filteredArticles = mutableListOf<Article>()

        for (article in articles) {
            if(board_id == 0) {
                if (article.title.contains(searchKeyword)) {
                    filteredArticles.add(article)
                }
            }else{
                if (board_id == article.board_id && article.title.contains(searchKeyword)) {
                    filteredArticles.add(article)
                }
            }
        }

        return filteredArticles
    }

    private fun getPageFilteredArticles(articles: List<Article>, page: Int, itemsCountInAPage: Int): List<Article> {
        val filteredArticles = mutableListOf<Article>()

        val offsetCount = (page - 1) * itemsCountInAPage

        val startIndex = articles.lastIndex - offsetCount
        var endIndex = startIndex - (itemsCountInAPage - 1)

        if (endIndex < 0) {
            endIndex = 0
        }

        for (i in startIndex downTo endIndex) {
            filteredArticles.add(articles[i])
        }

        return filteredArticles
    }
}

object memberRepository{
    private val members = mutableListOf<Member>()
    private var mem_num = 0
    fun getMember(num:Int):Member{
        return  members[num]
    }
    fun userJoin(join_id:String, join_passwd:String,join_name:String,join_nickname:String,join_phone:String, join_email:String ):Int{
        var num = mem_num
        try {
            members.add(Member(num,join_id, join_passwd, join_name, join_nickname, join_phone, join_email))
            mem_num += 1
            return 0
        }catch (e:Exception) {
            return 1
        }
    }

    fun userLogin(join_id: String, join_passwd: String):Member{
        var chk:Member = Member(0,"","","","","","")
        for(i in members){
            if(i.login_id == join_id){
                chk.login_id = "pass_fail"
                if(i.login_passwd == join_passwd){
                    chk = i
                }
            }
        }

        return chk
    }

    fun makeTestMember(){
        for(i in 0 .. 10){
            var id = "admin$i"
            var pass = "admin"
            var name = "admin$i"
            var tell = "비공개"
            var email = "admin@admin.com"

            userJoin(id,pass, name, tell, "관리자",email)
        }
    }
}

data class Board(
    var boardId:Int,
    var boardName:String
){}

object boardRepository{
    var boards = mutableListOf<Board>()
    fun boardCheckInt(board_id: Int):Boolean{
        for(i in boards){
            if(i.boardId == board_id) return false
        }
        return true
    }
    fun boardCheck(board_id: Int, boardName:String):Boolean{
        for(i in boards){
            if(i.boardId == board_id) return false
            else if(i.boardName == boardName) return false

        }
        return true
    }

    fun boardAdd(board_id: Int, boardName:String){
        boards.add(Board(board_id, boardName))
    }

    fun makeTestBoard(){
        boardRepository.boardAdd(0, "공지")
        boardRepository.boardAdd(1,"자유")
    }
}

//fun Day00_teach(){
//
//}
//fun Day00_addP(){
//
//}

var articlesLastId = 0

val articles = mutableListOf<Article>()

var articleTest: Article? = null

fun getArticleById(id: Int): Article? {
    for (article in articles) {
        if (article.id == id) {
            return article
        }
    }

    return null
}

fun getArticleById_search(id: Int, articleSearch:List<Article>): Article? {
    for (article in articleSearch) {
        if (article.id == id) {
            return article
        }
    }

    return null
}

fun addArticle(title: String, body: String): Int {
    val id = articlesLastId + 1
    val regDate = Util.getNowDateStr()
    val updateDate = Util.getNowDateStr()
    var ran = Random()
    val article = Article(id, regDate, updateDate, title, body,0, ran.nextInt(1))
    articles.add(article)

    articlesLastId = id

    return id
}

fun makeTestArticles() {
    for (id in 1..100) {
        val title = "제목$id"
        val body = "내용_$id"
        addArticle(title, body)
    }
}

data class Article(
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
fun readLineTrim() = readLine()!!.trim()

object Util {
    fun getNowDateStr(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

        return dateFormat.format(System.currentTimeMillis())
    }
}
/* 유틸관련 끝 */
