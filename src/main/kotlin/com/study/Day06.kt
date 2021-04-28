package com.study

import java.lang.Exception
import java.util.Random

fun Day06(){
    println("---- Day06 ----")
    println("학습 요약: 회원가입, 로그인, 로그인후 글쓰기, 작성자 리스트에 추가ㅁ")
    println()
    println("1번 : 나가기 , 2번 : 해당 나의 프로그램 보기 , 3번: 선생님 프로그램, 4번 : 추가 프로그램 ")
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
    articleRepository.makeTestArticles()
    val login_status = 0
    var login_info: Member? = null
    while (true) {
        print("명령어) ")
        val command = readLineTrim()

        val rq = Rq(command)

        when (rq.actionPath) {
            "/system/exit" -> {
                println("프로그램을 종료합니다.")
                break
            }
            "/article/list" -> {
                val page = rq.getIntParam("page", 1)
                val searchKeyword = rq.getStringParam("searchKeyword", "")

                val filteredArticles = articleRepository.getFilteredArticles(searchKeyword, page, 10)

                println("번호 /     작성날짜     /     갱신날짜      / 제목 / 내용 / 작성자")

                for (article in filteredArticles) {
                    println("${article.id} / ${article.regDate} / ${article.updateDate} / ${article.title} / ${memberRepository.getMember(article.member_num).login_id}")
                }
            }
            "/article/detail" -> {
                val id = rq.getIntParam("id", 0)

                if (id == 0) {
                    println("id를 입력해주세요.")
                    continue
                }

                val article = articleRepository.getArticleById(id)

                if (article == null) {
                    println("${id}번 게시물은 존재하지 않습니다.")
                    continue
                }

                println("번호 : ${article.id}")
                println("작성날짜 : ${article.regDate}")
                println("갱신날짜 : ${article.updateDate}")
                println("제목 : ${article.title}")
                println("내용 : ${article.body}")
            }
            "/article/modify" -> {
                val id = rq.getIntParam("id", 0)

                if (id == 0) {
                    println("id를 입력해주세요.")
                    continue
                }

                val article = articleRepository.getArticleById(id)

                if (article == null) {
                    println("${id}번 게시물은 존재하지 않습니다.")
                    continue
                }

                print("${id}번 게시물 새 제목 : ")
                val title = readLineTrim()
                print("${id}번 게시물 새 내용 : ")
                val body = readLineTrim()

                articleRepository.modifyArticle(id, title, body)

                println("${id}번 게시물이 수정되었습니다.")
            }
            "/article/delete" -> {
                val id = rq.getIntParam("id", 0)

                if (id == 0) {
                    println("id를 입력해주세요.")
                    continue
                }

                val article = articleRepository.getArticleById(id)

                if (article == null) {
                    println("${id}번 게시물은 존재하지 않습니다.")
                    continue
                }

                articleRepository.deleteArticle(article)
            }

            "/article/write" -> {
                if(login_info ==null){
                    println("알림) 글쓰기는 로그인 후 이용해주세요.")
                }else {
                    print("제목 : ")
                    val title = readLineTrim()
                    print("내용 : ")
                    val body = readLineTrim()

                    val id = articleRepository.addArticle(title, body,login_info.login_num)

                    println("${id}번 게시물이 작성되었습니다.")
                }
            }

            "/article/join" ->{
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

            "/article/login" ->{
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

        }
    }

    println("== SIMPLE SSG 끝 ==")
}


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

    fun addArticle(title: String, body: String, user_num:Int):Int {
        val id = ++lastId
        val regDate = Util.getNowDateStr()
        val updateDate = Util.getNowDateStr()
        articles.add(Article(id, regDate, updateDate, title, body, user_num))
        return id
    }

    fun makeTestArticles() {
        val random = Random()
        for (id in 1..100) {
            addArticle("제목_$id", "내용_$id", random.nextInt(11))
        }
    }

    fun modifyArticle(id: Int, title: String, body: String) {
        val article = getArticleById(id)!!

        article.title = title
        article.body = body
        article.updateDate = Util.getNowDateStr()
    }

    fun getFilteredArticles(searchKeyword: String, page: Int, itemsCountInAPage: Int): List<Article> {
        val filtered1Articles = getSearchKeywordFilteredArticles(articles, searchKeyword)
        val filtered2Articles = getPageFilteredArticles(filtered1Articles, page, itemsCountInAPage)

        return filtered2Articles
    }

    private fun getSearchKeywordFilteredArticles(articles: List<Article>, searchKeyword: String): List<Article> {
        val filteredArticles = mutableListOf<Article>()

        for (article in articles) {
            if (article.title.contains(searchKeyword)) {
                filteredArticles.add(article)
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


//fun Day00_teach(){
//
//}
//fun Day00_addP(){
//
//}
