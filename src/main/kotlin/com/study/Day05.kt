package com.study

import java.lang.Exception
import java.lang.NullPointerException

fun Day05(){
    println("============================ Day 05 ============================= ")
    println("학습 요약: 액션Path,파라미터  ...  \n" +
            "init 이란? 일종의 초기화 블록코틀린 문법 -> 객체 생성시 딱 한번 실행된다. \n" +
            "mapOf() : 읽기 전용으로 만든다. \n" +
            "toMap() : 리스트를 Map 으로 바꾼다.")
    println()
    println("1번 : 나가기 , 이 날 배운 코드는 Day06 으로 모두 옮김")
    println("=================================================================== ")

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


//fun Day05_P(){
//    println("==== Simple SSG 시작 =====")
//    while (true){
//        print("명령어) ")
//        val command = readLineTrim()
//
//        val rq = Rq(command)
////        println("rq.actionPath : ${rq.actionPath}, rq.paramMap : ${rq.paramMap}")
////        println("\${rq.getStringParam(\"title\", \"1\") = 리턴 값 : ${rq.getStringParam("title", "1")}, ${rq.getStringParam("title", "1") == "1"}")
////        println("rq.getIntParam(\"id\",-1) = 리턴 값 : ${rq.getIntParam("id",-1)} , ${rq.getIntParam("id",-1)==-1}")
//
//
//    }
//}

//class Rq(command:String){
//
//    val actionPath: String
//    val paramMap: Map<String, String>
//
//    init {
//        val commandBits = command.split("?", limit = 2)
//        actionPath = commandBits[0].trim()
//        val queryStr = if (commandBits.lastIndex == 1 && commandBits[1].isNotEmpty()) {
//            commandBits[1].trim()
//        } else {
//            ""
//        }

//        paramMap = if (queryStr.isEmpty()) {
//            mapOf()
//        } else {
//            val paramMapTemp = mutableMapOf<String, String>()
//
//            // queryStr = id=1&body=2&title=3&age
//            val queryStrBits = queryStr.split("&")
//
//            for (queryStrBit in queryStrBits) {
                // queryStrBit = id=1
//                val queryStrBitBits = queryStrBit.split("=", limit = 2)
//                val paramName = queryStrBitBits[0]
//                val paramValue = if (queryStrBitBits.lastIndex == 1 && queryStrBitBits[1].isNotEmpty()) {
//                    queryStrBitBits[1].trim()
//                } else {
//                    ""
//                }
//
//                if (paramValue.isNotEmpty()) {
//                    paramMapTemp[paramName] = paramValue
//                }
//            }
//
//            paramMapTemp.toMap()
//        }
//    }
//
//    fun getStringParam(key:String, default:String): String {
//        return try {
//            paramMap[key]!!
//        }catch (e:NullPointerException){
//            default
//        }
//    }
//
//    fun getIntParam(key:String, default: Int): Int {
//        var result:Int
//        try{
//            result = paramMap[key]!!.trim().toInt()
//        }catch (e:NullPointerException){
//            result = default
//        }catch (e:NumberFormatException){
//            result = default
//        }

//        return try{
//            paramMap[key]!!.trim().toInt()
//        }catch (e:Exception){
//            default
//        }


//        if(paramMap[key] == null){
//            return default
//        }else{
//            return paramMap[key]!!.trim().toInt()
//       }
//    }
//}




//fun Day00_teach(){
//
//}
//fun Day00_addP(){
//
//}
