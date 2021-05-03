package com.study

import com.google.gson.Gson
import java.io.FileReader
import java.io.IOException
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.nio.file.StandardOpenOption


fun Day08(){
    println("======================= Day 08 ============================= ")
    println("학습 요약: 데이터 저장 -> 보조 기억 장치 이용, json " +
            "\n json 이용하여 파일 읽기 쓰기" +
            "\n Files.write()  , FileReader()")
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
        test()
    }else if(chk == 3){
//        Day00_teach()
    }else if(chk == 4){
//        Day00_addP()
    }else{
        println("메인 페이지로 이동")
    }

    println()
}


data class User (
    val name: String,
    val email: String,
    val age: Int = 20
)

fun test() {
    val path ="C:\\Users\\yungi\\IdeaProjects\\Study\\src\\main\\assets\\data.json"
    val text = """
            {
                "name" : "Ready Kim",
                "email" : "ready.kim@gmail.com"
            }
        """.trimIndent()
    
    try{
        Files.write(Paths.get(path), text.toByteArray(), StandardOpenOption.CREATE)
    }catch (e:IOException){
        println("에러")
    }
    
    try {
        val read = FileReader(path)
        val text = """
            {
                "name" : "Ready Kim",
                "email" : "ready.kim@gmail.com"
            }
        """.trimIndent()
        val user = Gson().fromJson(text, User::class.java)
        println(user)
    }catch (e:Exception){
        println(e.message)
    }
}