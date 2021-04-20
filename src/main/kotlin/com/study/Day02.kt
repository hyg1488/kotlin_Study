package com.study

fun Day02() {
    println("== 회원관리 프로그램 시작 ==")

    print("회원 수 : ")
    val membersCount = readLine()!!.trim().toInt()

    val members = Array<Member?>(membersCount) { null }

    println("= ${membersCount}명의 회원 정보를 입력합니다. =")

    for (i in 0 until membersCount) {
        val id = i + 1
        print("${i + 1}번째 회원의 이름 : ")
        val name = readLine()!!.trim()
        print("${i + 1}번째 회원의 나이 : ")
        val age = readLine()!!.trim().toInt()
        print("${i + 1}번째 회원의 성별(M/W) : ")
        val gender = readLine()!!.trim()[0]

        val member = Member(id, name, age, gender)
        members[i] = member
    }

    println("= 입력하신 회원 리스트 =")
    println("번호 / 나이 / 성별 / 이름")

    for (member in members) {
        println("${member?.id}    / ${member?.age}   / ${member?.getGenderKor()} / ${member?.name}")
    }

    println("== 회원관리 프로그램 끝 ==")
}

data class Member(
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