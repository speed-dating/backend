package com.backend.speed_dating.config

import com.backend.speed_dating.common.status.CountryEnum
import com.backend.speed_dating.common.status.Gender
import com.backend.speed_dating.dating.entity.Dating
import com.backend.speed_dating.dating.entity.Participant
import com.backend.speed_dating.dating.repository.DatingRepository
import com.backend.speed_dating.dating.repository.ParticipantRepository
import com.backend.speed_dating.user.entity.Member
import com.backend.speed_dating.user.repository.GalleryRepository
import com.backend.speed_dating.user.repository.MemberRepository
import com.backend.speed_dating.user.repository.TagRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.LocalDate

@Configuration
class DataInitializer(
    private val memberRepository: MemberRepository,
    private val datingRepository: DatingRepository,
    private val participantRepository : ParticipantRepository,
    private val galleryRepository: GalleryRepository,
    private val tagRepository: TagRepository,
) {

    @Bean
    fun initData(): CommandLineRunner {
        return CommandLineRunner {
            // 웹에 존재하는 프로필 이미지 URL 리스트
            // 모든 데이터를 삭제
            participantRepository.deleteAll()
            datingRepository.deleteAll()
            memberRepository.deleteAll()
            tagRepository.deleteAll()
            galleryRepository.deleteAll()


            val profileImageUrls = listOf(
                "https://images.unsplash.com/photo-1544005313-94ddf0286df2?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzNjUyOXwwfDF8c2VhcmNofDF8fG1hbGV8ZW58MHx8fHwxNjE2NDA2NzEw&ixlib=rb-1.2.1&q=80&w=400",
                "https://images.unsplash.com/photo-1502764613149-7f1d229e2302?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzNjUyOXwwfDF8c2VhcmNofDJ8fGZlbWFsZXxlbnwwfHx8fDE2MTY0MDY3MTg&ixlib=rb-1.2.1&q=80&w=400",
                "https://images.unsplash.com/photo-1502685104226-ee32379fefbe?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzNjUyOXwwfDF8c2VhcmNofDR8fG1hbGV8ZW58MHx8fHwxNjE2NDA2NzEw&ixlib=rb-1.2.1&q=80&w=400",
                "https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzNjUyOXwwfDF8c2VhcmNofDV8fGZlbWFsZXxlbnwwfHx8fDE2MTY0MDY3MTg&ixlib=rb-1.2.1&q=80&w=400",
                "https://images.unsplash.com/photo-1488426862026-3ee34a7d66df?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzNjUyOXwwfDF8c2VhcmNofDZ8fG1hbGV8ZW58MHx8fHwxNjE2NDA2NzEw&ixlib=rb-1.2.1&q=80&w=400",
                "https://images.unsplash.com/photo-1511367461989-f85a21fda167?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzNjUyOXwwfDF8c2VhcmNofDd8fGZlbWFsZXxlbnwwfHx8fDE2MTY0MDY3MTg&ixlib=rb-1.2.1&q=80&w=400",
                "https://images.unsplash.com/photo-1524504388940-b1c1722653e1?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzNjUyOXwwfDF8c2VhcmNofDh8fG1hbGV8ZW58MHx8fHwxNjE2NDA2NzEw&ixlib=rb-1.2.1&q=80&w=400",
                "https://images.unsplash.com/photo-1508214751196-bcfd4ca60f91?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzNjUyOXwwfDF8c2VhcmNofDl8fGZlbWFsZXxlbnwwfHx8fDE2MTY0MDY3MTg&ixlib=rb-1.2.1&q=80&w=400",
                "https://images.unsplash.com/photo-1517841905240-472988babdf9?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzNjUyOXwwfDF8c2VhcmNofDEwfG1hbGV8ZW58MHx8fHwxNjE2NDA2NzEw&ixlib=rb-1.2.1&q=80&w=400",
                "https://images.unsplash.com/photo-1527980965255-d3b416303d12?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzNjUyOXwwfDF8c2VhcmNofDExfGZlbWFsZXxlbnwwfHx8fDE2MTY0MDY3MTg&ixlib=rb-1.2.1&q=80&w=400",
                "https://images.unsplash.com/photo-1526170375885-4d8ecf77b99f?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzNjUyOXwwfDF8c2VhcmNofDEyfG1hbGV8ZW58MHx8fHwxNjE2NDA2NzEw&ixlib=rb-1.2.1&q=80&w=400",
                "https://images.unsplash.com/photo-1547425260-76bcadfb4f2c?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzNjUyOXwwfDF8c2VhcmNofDEzfGZlbWFsZXxlbnwwfHx8fDE2MTY0MDY3MTg&ixlib=rb-1.2.1&q=80&w=400",
                "https://images.unsplash.com/photo-1517705008127-8c9187dda3e7?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzNjUyOXwwfDF8c2VhcmNofDE0fG1hbGV8ZW58MHx8fHwxNjE2NDA2NzEw&ixlib=rb-1.2.1&q=80&w=400",
                "https://images.unsplash.com/photo-1526045612212-70caf35c14df?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzNjUyOXwwfDF8c2VhcmNofDE1fGZlbWFsZXxlbnwwfHx8fDE2MTY0MDY3MTg&ixlib=rb-1.2.1&q=80&w=400",
                "https://images.unsplash.com/photo-1531123897727-8f129e1688ce?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzNjUyOXwwfDF8c2VhcmNofDE2fG1hbGV8ZW58MHx8fHwxNjE2NDA2NzEw&ixlib=rb-1.2.1&q=80&w=400",
                "https://images.unsplash.com/photo-1532938911079-1b06ac7ceec7?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzNjUyOXwwfDF8c2VhcmNofDE3fGZlbWFsZXxlbnwwfHx8fDE2MTY0MDY3MTg&ixlib=rb-1.2.1&q=80&w=400",
                "https://images.unsplash.com/photo-1539914287624-4fbc5a7bcd8d?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzNjUyOXwwfDF8c2VhcmNofDE4fG1hbGV8ZW58MHx8fHwxNjE2NDA2NzEw&ixlib=rb-1.2.1&q=80&w=400",
                "https://images.unsplash.com/photo-1541698444083-023c97d3f4b6?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzNjUyOXwwfDF8c2VhcmNofDIwfG1hbGV8ZW58MHx8fHwxNjE2NDA2NzEw&ixlib=rb-1.2.1&q=80&w=400",
                "https://images.unsplash.com/photo-1544653516-57c972f063e9?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzNjUyOXwwfDF8c2VhcmNofDIxfGZlbWFsZXxlbnwwfHx8fDE2MTY0MDY3MTg&ixlib=rb-1.2.1&q=80&w=400",
                "https://images.unsplash.com/photo-1541635680542-168610c67ef6?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzNjUyOXwwfDF8c2VhcmNofDE5fGZlbWFsZXxlbnwwfHx8fDE2MTY0MDY3MTg&ixlib=rb-1.2.1&q=80&w=400",
            )

            // 갤러리 이미지 URL 리스트
            val galleryImageUrls = listOf(
                "https://images.unsplash.com/photo-1551963831-b3b1ca40c98e?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzNjUyOXwwfDF8c2VhcmNofDF8fGZvb2R8ZW58MHx8fHwxNjE2NDA2NzEw&ixlib=rb-1.2.1&q=80&w=400",
                "https://images.unsplash.com/photo-1551782450-a2132b4ba21d?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzNjUyOXwwfDF8c2VhcmNofDJ8fGZvb2R8ZW58MHx8fHwxNjE2NDA2NzEw&ixlib=rb-1.2.1&q=80&w=400",
                "https://images.unsplash.com/photo-1551218808-94e220e084d2?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzNjUyOXwwfDF8c2VhcmNofDN8fGZvb2R8ZW58MHx8fHwxNjE2NDA2NzEw&ixlib=rb-1.2.1&q=80&w=400",
                "https://images.unsplash.com/photo-1551446591-142875a901a1?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzNjUyOXwwfDF8c2VhcmNofDR8fGZvb2R8ZW58MHx8fHwxNjE2NDA2NzEw&ixlib=rb-1.2.1&q=80&w=400",
                "https://images.unsplash.com/photo-1550439062-609e1531270e?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzNjUyOXwwfDF8c2VhcmNofDV8fGZvb2R8ZW58MHx8fHwxNjE2NDA2NzEw&ixlib=rb-1.2.1&q=80&w=400",
                "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzNjUyOXwwfDF8c2VhcmNofDZ8fGZvb2R8ZW58MHx8fHwxNjE2NDA2NzEw&ixlib=rb-1.2.1&q=80&w=400",
                "https://images.unsplash.com/photo-1533777324565-a040eb52fac1?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzNjUyOXwwfDF8c2VhcmNofDd8fGZvb2R8ZW58MHx8fHwxNjE2NDA2NzEw&ixlib=rb-1.2.1&q=80&w=400",
                "https://images.unsplash.com/photo-1514975672025-8c432f0b6674?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzNjUyOXwwfDF8c2VhcmNofDh8fGZvb2R8ZW58MHx8fHwxNjE2NDA2NzEw&ixlib=rb-1.2.1&q=80&w=400",
                "https://images.unsplash.com/photo-1540189549336-e6e99c3679fe?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzNjUyOXwwfDF8c2VhcmNofDl8fGZvb2R8ZW58MHx8fHwxNjE2NDA2NzEw&ixlib=rb-1.2.1&q=80&w=400",
                "https://images.unsplash.com/photo-1533777324565-a040eb52fac1?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzNjUyOXwwfDF8c2VhcmNofDF8fGZvb2R8ZW58MHx8fHwxNjE2NDA2NzEw&ixlib=rb-1.2.1&q=80&w=400"
            )

            // 회원 생성
            val members = mutableListOf<Member>()

            // 루프를 통한 회원 생성
            members.addAll(
                (1..20).map { i ->
                    Member(
                        gender = if (i % 2 == 0) Gender.MALE else Gender.FEMALE,
                        phoneNumber = "010-1234-${i.toString().padStart(4, '0')}",
                        nickname = "User$i",
                        country = CountryEnum.KR,
                        birthDate = LocalDate.of(1990 + (i % 10), i % 12 + 1, i % 28 + 1),
                        profileImageUrl = profileImageUrls[i - 1],
                        introduce = "some introduction text"
                    )
                }
            )

            // 추가로 특정 회원 추가
            members.add(
                Member(
                    gender = Gender.MALE,
                    phoneNumber = "01099645997",
                    nickname = "shy",
                    country = CountryEnum.KR,
                    birthDate = LocalDate.of(1997, 3, 28),
                    profileImageUrl = "http://example.com/profile.jpg",
                    introduce = "specific user introduction"
                )
            )




            memberRepository.saveAll(members)

            // 데이팅 이미지 URL 리스트
            val datingImageUrls = listOf(
                "https://images.unsplash.com/photo-1485811901755-d0afa3c218f5?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzNjUyOXwwfDF8c2VhcmNofDEwfGZlYXJzfDB8fHx8fDE2NTE1MTMyNDM&ixlib=rb-1.2.1&q=80&w=400",
                "https://images.unsplash.com/photo-1500530855697-b586d89ba3ee?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzNjUyOXwwfDF8c2VhcmNofDJ8fGZlYXJzfDB8fHx8fDE2NTE1MTMyNDM&ixlib=rb-1.2.1&q=80&w=400",
                "https://images.unsplash.com/photo-1494790108377-be9c29b29330?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzNjUyOXwwfDF8c2VhcmNofDN8fGZlYXJzfDB8fHx8fDE2NTE1MTMyNDM&ixlib=rb-1.2.1&q=80&w=400",
                "https://images.unsplash.com/photo-1532614338840-099d0d8e4c53?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzNjUyOXwwfDF8c2VhcmNofDR8fGZlYXJzfDB8fHx8fDE2NTE1MTMyNDM&ixlib=rb-1.2.1&q=80&w=400",
                "https://images.unsplash.com/photo-1495406279376-54f20180a8b3?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzNjUyOXwwfDF8c2VhcmNofDV8fGZlYXJzfDB8fHx8fDE2NTE1MTMyNDM&ixlib=rb-1.2.1&q=80&w=400",
                "https://images.unsplash.com/photo-1492562080023-ab3db95bfbce?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzNjUyOXwwfDF8c2VhcmNofDZ8fGZlYXJzfDB8fHx8fDE2NTE1MTMyNDM&ixlib=rb-1.2.1&q=80&w=400",
                "https://images.unsplash.com/photo-1485217988980-11786ced9454?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzNjUyOXwwfDF8c2VhcmNofDd8fGZlYXJzfDB8fHx8fDE2NTE1MTMyNDM&ixlib=rb-1.2.1&q=80&w=400",
                "https://images.unsplash.com/photo-1491975474562-1f4e30bc9468?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzNjUyOXwwfDF8c2VhcmNofDh8fGZlYXJzfDB8fHx8fDE2NTE1MTMyNDM&ixlib=rb-1.2.1&q=80&w=400",
                "https://images.unsplash.com/photo-1512428559087-a4a5e17a1033?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzNjUyOXwwfDF8c2VhcmNofDl8fGZlYXJzfDB8fHx8fDE2NTE1MTMyNDM&ixlib=rb-1.2.1&q=80&w=400",
                "https://images.unsplash.com/photo-1517849845537-4d257902454a?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzNjUyOXwwfDF8c2VhcmNofDEwfGZlYXJzfDB8fHx8fDE2NTE1MTMyNDM&ixlib=rb-1.2.1&q=80&w=400"
            )

            // 데이팅 생성
            val datings = (1..10).map { i ->
                Dating(
                    title = "Speed Dating Event $i",
                    description = "A fun speed dating event $i",
                    imageUrl = datingImageUrls[i - 1],
                    price = 20.0,
                    startDate = LocalDate.now().plusDays(i.toLong()),
                    maleCapacity = 10,
                    femaleCapacity = 10,
                    owner = members[i - 1]
                )
            }

            datingRepository.saveAll(datings)

            // 참가자 생성
            datings.forEach { dating ->
                val participantIndexes = (0 until members.size).shuffled().take(10)
                participantIndexes.forEach { index ->
                    val participant = Participant(
                        dating = dating,
                        member = members[index]
                    )
                    // 참가자 정보 저장
                    dating.participants?.plus(participant)
                }
            }

            datingRepository.saveAll(datings) // 참가자 정보가 포함된 데이팅 저장
        }
    }
}