package com.study
import java.time.LocalDateTime

import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.io.File

fun Day10() {
    println("======================= Day 00 ============================= ")
    println("학습 요약: .contain() : 문자열이 포함 되있는지 확인 " +
            "\n        lastIndex(), jackson 사용 ")
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
        day10_P()
    }else if(chk == 3){
//        Day00_teach()
    }else if(chk == 4){
//        Day00_addP()
    }else{
        println("메인 페이지로 이동")
    }

    println()
}
fun day10_P(){
    val mapper   = jacksonObjectMapper()
    val articles = ArrayList<Article03>()

    // Add elem to collection.
    (1..3).forEach { n ->
        articles.add(
            Article03(
                "MyArticle $n",
                "2021-01-04",
                0,
                "Hello, Jackson! $n"))
    }


    // Convert Collection to JSON.
    mapper
        .writerWithDefaultPrettyPrinter()
        .writeValue(
            File("C:\\Users\\yungi\\IdeaProjects\\Study\\src\\main\\assets\\article.json"),
            articles
        )
    val mapper2   = jacksonObjectMapper()
    val articles02 = mapper2.readValue<ArrayList<Article03>>(File("C:\\Users\\yungi\\IdeaProjects\\Study\\src\\main\\assets\\article.json"))

    println(articles02)
}


data class Article03(
    // 게시글 제목
    val title : String = "",

    // 게시글 등록일자
    val date  : String? = null,

    // 게시글 조회회수
    val viewCnt  : Int = 0,

    // 게시글 내용
    val content : String = ""
)