package com.study

fun Day03(){
    println("---- Day03 ----")
    println("학습 요약: MutableList , ")
    println()
    println("1번 : 나가기 , 2번 : 해당 나의 프로그램 보기 , 3번: 선생님 프로그램, 4번 : Day02 추가 프로그램 ")
    println("5번 : day02 프로그램에 이름 정렬 추가")
    print("입력 : ")
    var chk = readLine()!!.trim().toInt()
    println()
    if(chk == 1){
        println("메인 페이지로 이동")
    }else if(chk == 2){
        Day03_02()
    }else if(chk == 3){
        Day03_01()
    }else if(chk == 4){
        Day03_day02()
    }else if(chk == 5){
        Day03_day02_name()
    }else{
        println("메인 페이지로 이동")
    }

    println()
}

/*
== 프로그램 시작 ==
숫자(띄워쓰기로 구분) : 5 3 8
오름차순 정렬 결과 : 3 5 8
내림차순 정렬 결과 : 8 5 3
== 프로그램 끝 ==
*/

fun Day03_01() {
    println("== 프로그램 시작 ==")
    print("숫자(띄워쓰기로 구분) : ")
    val numbers = readLine()!!.trim().split(" ").map { it.toInt() }.toMutableList()

    bubbleSort(numbers)

    print("오름차순 정렬 결과 : ")
    printNumbers(numbers)

    print("내림차순 정렬 결과 : ")
    printNumbersReversed(numbers)

    println("== 프로그램 끝 ==")
}

fun bubbleSort(numbers: MutableList<Int>) {

}

fun printNumbers(numbers: List<Int>) {
    numbers.forEach {
        print("$it ")
    }
    println()
}

fun printNumbersReversed(numbers: List<Int>) {
    numbers.withIndex().reversed().forEach {
        print("${it.value} ")
    }
    println()
}

fun Day03_02() {
    println("Day01 - ")
    println("=== 정렬 프로그램 시작 ===")
    print("숫자(띄워쓰기로 구분) : ")
    var num = readLine()!!.split(" ").map { it.toInt() }.toMutableList()

    print("오름차순 정렬 결과 : ")

    sort_02(num)
    print("내림차순 정렬 결과 : ")
    reserve_02(num)
}



fun sort_02(num:MutableList<Int>){
    for(i in num.indices){
        for(x in 0 until num.size){
            if(num[i]<num[x]){
                var idx = num[i]
                num[i] = num[x]
                num[x] = idx
            }
        }
    }

    for(i in num.indices){
        print("${num[i]} ")
    }
    println()
}

fun reserve_02(num:MutableList<Int>){
    for(i in num.indices){
        for(x in 0 until num.size){
            if(num[i]>num[x]){
                var idx = num[i]
                num[i] = num[x]
                num[x] = idx
            }
        }
    }


    for(i in num.indices){
        print("${num[i]} ")
    }
    println()
}


fun Day03_day02() {
    println("== 회원관리 프로그램 시작 (나이 정렬)==")

    print("회원 수 : ")
    val membersCount = readLine()!!.trim().toInt()

    val members = Array<Member_d3?>(membersCount) { null }

    println("= ${membersCount}명의 회원 정보를 입력합니다. =")

    for (i in 0 until membersCount) {
        val id = i + 1
        print("${i + 1}번째 회원의 이름 : ")
        val name = readLine()!!.trim()
        print("${i + 1}번째 회원의 나이 : ")
        val age = readLine()!!.trim().toInt()
        print("${i + 1}번째 회원의 성별(M/W) : ")
        val gender = readLine()!!.trim()[0]

        val member = Member_d3(id, name, age, gender)
        members[i] = member
    }

    println("= 입력하신 회원 리스트 =")
    println("번호 / 나이 / 성별 / 이름")

    for (member in members) {
        println("${member?.id}    / ${member?.age}   / ${member?.getGenderKor()} / ${member?.name}")
    }

    println("== 나이 정렬 추가 ==")

    for(i in members.indices-1){
        var age01:Int = members[i]!!.age
        var age02:Int = members[i+1]!!.age
        if(age01 < age02){
            var mem: Member_d3? = members[i]
            members[i] = members[i+1]
            members[i+1] = mem
        }
    }

    for (member in members) {
        println("${member?.id}    / ${member?.age}   / ${member?.getGenderKor()} / ${member?.name}")
    }

    println("== 회원관리 프로그램 끝 ==")
}

data class Member_d3(
    val id: Int,
    val name: String,
    val age: Int,
    val gender: Char
) {

    fun getGenderKor(): String = when(gender) {
        'w' -> "여자"
        else -> "남자"
    }
}


// 이름 정렬 포함
fun Day03_day02_name() {
    println("== 회원관리 프로그램 시작 (나이, 이름 정렬)==")

    print("회원 수 : ")
    val membersCount = readLine()!!.trim().toInt()

    val members = Array<Member_d3?>(membersCount) { null }

    println("= ${membersCount}명의 회원 정보를 입력합니다. =")

    for (i in 0 until membersCount) {
        val id = i + 1
        print("${i + 1}번째 회원의 이름 : ")
        val name = readLine()!!.trim()
        print("${i + 1}번째 회원의 나이 : ")
        val age = readLine()!!.trim().toInt()
        print("${i + 1}번째 회원의 성별(M/W) : ")
        val gender = readLine()!!.trim()[0]

        val member = Member_d3(id, name, age, gender)
        members[i] = member
    }

    println("= 입력하신 회원 리스트 =")
    println("번호 / 나이 / 성별 / 이름")

    for (member in members) {
        println("${member?.id}    / ${member?.age}   / ${member?.getGenderKor()} / ${member?.name}")
    }
    println()
    println("== 나이, 이름 정렬 추가 ==")
    var cnt = 0

    while (cnt < members.size - 1) {
        for (i in 0 until members.size - cnt - 1) {
            var age01: Int = members[i]!!.age
            var age02: Int = members[i + 1]!!.age
            if (age01 > age02) {
                var mem: Member_d3? = members[i]
                members[i] = members[i + 1]
                members[i + 1] = mem
            } else if (age01 == age02) {
                for (j in 0 until members[i]!!.name.length) {
                    var mem: Member_d3? = members[i + 1]
                    if (members[i]!!.name[j] > members[i + 1]!!.name[j]) {
                        members[i + 1] == members[i]
                        members[i] = mem
                    }
                }
            }
        }

        cnt++
    }

    println("번호 / 나이 / 성별 / 이름")

    for (member in members) {
        println("${member?.id}    / ${member?.age}   / ${member?.getGenderKor()} / ${member?.name}")
    }


    println("== 회원관리 프로그램 끝 ==")
    println("여기서 나온 문제는 Day04 에서 해결함 - ")

}
