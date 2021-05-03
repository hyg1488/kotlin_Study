package com.study


fun Day00(){
    println("======================= Day 00 ============================= ")
    println("학습 요약: ")
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
//        Day00_P()
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

data class arr04(
    var id:Int,
    var name:String
){}
