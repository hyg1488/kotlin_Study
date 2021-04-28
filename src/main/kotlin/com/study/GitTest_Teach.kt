package com.study

import java.text.SimpleDateFormat

fun GitTest_Teach() {
    println("== 게시판 관리 프로그램 시작 ==")

    articleRepository.makeTestArticles()

    while (true) {
        print("명령어) ")
        val command = readLineTrim()

        when {
            command == "system exit" -> {
                println("프로그램을 종료합니다.")
                break
            }
            command == "article write" -> {
                print("제목 : ")
                val title = readLineTrim()
                print("내용 : ")
                val body = readLineTrim()

                val id = addArticle(title, body)

                println("${id}번 게시물이 작성되었습니다.")
            }
            command.startsWith("article list ") -> {
                val commandBits = command.trim().split(" ")

                var page = 1
                var searchKeyword = ""

                if (commandBits.size == 4) {
                    searchKeyword = commandBits[2]
                    page = commandBits[3].toInt()
                } else if (commandBits.size == 3) {
                    page = commandBits[2].toInt()
                }

                val itemsCountInAPage = 5
                val offsetCount = (page - 1) * itemsCountInAPage

                val filteredarticles = getFilteredArticles(searchKeyword, offsetCount, itemsCountInAPage)

                println("번호 / 작성날짜 / 제목")

                for (article in filteredarticles) {
                    println("${article.id} / ${article.regDate} / ${article.title}")
                }
            }
            command.startsWith("article delete ") -> {
                val id = command.trim().split(" ")[2].toInt()

                val articleToDelete = getArticleById(id)

                if (articleToDelete == null) {
                    println("${id}번 게시물은 존재하지 않습니다.")
                    continue
                }

                articles.remove(articleToDelete)

                println("${id}번 게시물을 삭제하였습니다.")
            }
            command.startsWith("article modify ") -> {
                val id = command.trim().split(" ")[2].toInt()

                val articleToModify = getArticleById(id)

                if (articleToModify == null) {
                    println("${id}번 게시물은 존재하지 않습니다.")
                    continue
                }

                print("${id}번 게시물 새 제목 : ")
                articleToModify.title = readLineTrim()
                print("${id}번 게시물 새 내용 : ")
                articleToModify.body = readLineTrim()
                articleToModify.updateDate = Util.getNowDateStr()

                println("${id}번 게시물을 수정하였습니다.")
            }
            command.startsWith("article detail ") -> {
                val id = command.trim().split(" ")[2].toInt()

                val article = getArticleById(id)

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
            else -> {
                println("`$command` 은(는) 존재하지 않는 명령어 입니다.")
            }
        }
    }

    println("== 게시판 관리 프로그램 끝 ==")
}

/* 게시물 관련 시작 */


fun getFilteredArticles(searchKeyword: String, offsetCount: Int, takeCount: Int): List<Article> {
    var filtered1Articles = articles

    if (searchKeyword.isNotEmpty()) {
        filtered1Articles = mutableListOf<Article>()

        for (article in articles) {
            if (article.title.contains(searchKeyword)) {
                filtered1Articles.add(article)
            }
        }
    }

    val filtered2Articles = mutableListOf<Article>()

    val startIndex = filtered1Articles.lastIndex - offsetCount
    var endIndex = startIndex - takeCount + 1

    if (endIndex < 0) {
        endIndex = 0
    }

    for (i in startIndex downTo endIndex) {
        filtered2Articles.add(filtered1Articles[i])
    }

    return filtered2Articles
}
