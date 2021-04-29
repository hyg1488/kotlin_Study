package com.study

import java.text.SimpleDateFormat

fun GitTest() {
    println("== 게시판 관리 프로그램 시작 ==")

    makeTestArticles();

    loop@ while (true) {
        print("명령어) ")
        val command = readLineTrim()

        when {
            command == "system exit" -> {
                println("프로그램을 종료합니다.")
                break@loop
            }
            command.startsWith("article delete ") -> {
                val id = command.trim().split(" ")[2].toInt()

                var articleToDelete = getArticleById(id)

                if (articleToDelete == null) {
                    println("${id}번 게시물은 존재하지 않습니다.")
                    continue
                }

                articles.remove(articleToDelete)

                println("${id}번 게시물을 삭제하였습니다.")
            }
            command.startsWith("article modify ") -> {
                val id = command.trim().split(" ")[2].toInt()

                var articleToModify = getArticleById(id)

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

                var article = getArticleById(id)

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
            command == "article write" -> {
                print("제목 : ")
                val title = readLineTrim()
                print("내용 : ")
                val body = readLineTrim()

                val id = addArticle(title, body)

                println("${id}번 게시물이 작성되었습니다.")
            }
            command.startsWith("article list") -> {
                var totalSize = articles.size
                var totalPage = 0
                val itemCountInPage = 10

                if(totalSize%itemCountInPage == 0) {
                    totalPage = totalSize/itemCountInPage
                }else{
                    totalPage = totalSize/itemCountInPage+1
                }

                if(command.trim().split(" ").size == 2) {
                    println("번호 / 작성날짜 / 제목")

                    for (article in articles.reversed()) {
                        println("${article.id} / ${article.regDate} / ${article.title}")
                    }

                    println("Page 정보 : 총 ${totalPage}개의 페이지가 있습니다.")
                }else if(command.trim().split(" ").size == 3){
                    var id:Int = command.trim().split(" ")[2].toInt()

                    //10
                    // 100- 90 10
//                    var nowPageStart = totalSize-(id-1)*itemCountInPage
//                    var nowpageEnd = nowPageStart-itemCountInPage
                    var nowPageStart = id*itemCountInPage
                    var nowpageEnd = nowPageStart - itemCountInPage

                    // 1, 2, 3, 4
                    var view_article = mutableListOf<Article>()
                    for (i in nowpageEnd .. nowPageStart) {
                        var articleToList = getArticleById(i)
                        if (articleToList != null) {
                            view_article.add(articleToList)
                        }
                    }

                    for (article in view_article.reversed()) {
                        println("${article.id} / ${article.regDate} / ${article.title}")
                    }
                }else if(command.trim().split(" ").size >= 4){
                    var id:Int = 0
                    var search:String = ""
                    if(command.trim().split(" ").size == 4){
                        id = command.trim().split(" ")[3].toInt()
                        search = command.trim().split(" ")[2]
                    }else{
                        var k = command.trim().split(" ").size
                        for(i in 2 .. k-2){
                            search += command.trim().split(" ")[i]
                        }
                        id = command.trim().split(" ")[k-1].toInt()
                    }

                    var articleToSearch = mutableListOf<Article>()

                    for(i in articles){
                        if(i.title.startsWith(search)){
                            articleToSearch.add(i)
                        }
                    }

                    totalSize = articleToSearch.size-1
                    var nowPageStart = totalSize-(id-1)*itemCountInPage
                    var nowpageEnd = nowPageStart-itemCountInPage+1

                    if(totalSize%itemCountInPage == 0) {
                        totalPage = totalSize/itemCountInPage
                    }else{
                        totalPage = totalSize/itemCountInPage+1
                    }

                    println()
                    println("해당 검색결과가 총 ${totalSize}개 ,  ${totalPage} 페이지가 있습니다.")
                    println("검색 결과중 ${id} 페이지를 검색합니다.")

                    var search_view_article = mutableListOf<Article>()
                    for (i in nowpageEnd .. nowPageStart) {
                        if(i>=0) {
                            var searchId = articleToSearch[i].id
                            var articleToList = getArticleById(searchId)
                            if (articleToList != null) {
                                search_view_article.add(articleToList)
                            }
                        }
                    }

                    for (article in search_view_article.reversed()) {
                        println("${article.id} / ${article.regDate} / ${article.title}")
                    }

                }
            }
            else -> {
                println("`$command` 은(는) 존재하지 않는 명령어 입니다.")
            }
        }
    }

    println("== 게시판 관리 프로그램 끝 ==")
}

/* 게시물 관련 시작 */
// 가장 마지막에 입력된 게시물 번호
