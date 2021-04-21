package com.study

fun Day04(){
    println("---- Day04 ----")
    print("학습 요약: String 비교 가능 - ex) 김홍도 > 김기철 = ?  답 : ")
    println("김홍도" > "김기철")
    println("가나다라마바사아자차카타파하 순으로 사전에서 앞에 나오는 값이 더 작다.")
    println()
    println("1번 : 나가기 , 2번 : 해당 나의 프로그램 보기 , 3번: 선생님 프로그램 ")
    print("입력 : ")
    var chk = readLine()!!.trim().toInt()
    println()
    if(chk == 1){
        println("메인 페이지로 이동")
    }else if(chk == 2){

    }else if(chk == 3){
        Day04_teach()
    }else if(chk == 4){

    }else{
        println("메인 페이지로 이동")
    }

    println()
}
fun Day04_teach(){
    /*
입력데이터 1
-------------
5
한희수
30
w
김민희
30
w
홍길동
20
m
한희수
30
m
한희수
30
w

*/

    println("== 회원관리 프로그램 시작 ==")

    print("회원 수 : ")
    val membersCount = readLine()!!.trim().toInt()

    val members: Array<Member> = Array<Member?>(membersCount) { null } as Array<Member>

    println("= ${membersCount}명의 회원 정보를 입력합니다. =")

    for (i in members.indices) {
        val id = i + 1
        print("${i + 1}번째 회원의 이름 : ")
        val name = readLine()!!.trim()
        print("${i + 1}번째 회원의 나이 : ")
        val age = readLine()!!.trim().toInt()
        print("${i + 1}번째 회원의 성별(M/W) : ")
        val gender = readLine()!!.trim()[0]

        members[i] = Member(id, name, age, gender)
    }

    sortMembersByAgeAsc(members)

    println("= 입력하신 회원 리스트 =")
    println("번호 / 나이 / 성별 / 이름")

    for (member in members) {
        println("${member.id}    / ${member.age}   / ${member.getGenderKor()} / ${member.name}")
    }

    println("== 회원관리 프로그램 끝 ==")
}

// 나이 적은순 + 이름 순 + 여자 먼저 + 번호 앞번호 부터
fun isMemberBiggerThan(member1: Member, member2: Member): Boolean {
    if (member1.age != member2.age) return member1.age > member2.age
    else {
        if (member1.name != member2.name) return member1.name > member2.name
        else{
            if(member1.gender != member2.gender) return member1.gender < member2.gender
            else{
                return member1.id > member2.id
            }
        }
    }
}

fun sortMembersByAgeAsc(members: Array<Member>) {
    val maxDepth = members.size - 1

    for (depth in maxDepth downTo 1) {

        for (i in 0 until depth) {
            if (isMemberBiggerThan(members[i], members[i + 1])) {
                members[i] = members[i + 1].also { members[i + 1] = members[i] }
            }
        }
    }
}

