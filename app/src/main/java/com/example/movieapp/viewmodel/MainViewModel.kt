package com.example.movieapp.viewmodel

import android.util.Log
import kotlin.collections.*
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.R
import com.example.movieapp.data.Category
import com.example.movieapp.data.Movie
import com.example.movieapp.data.Seat
import com.example.movieapp.data.User
import com.google.type.Money
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(private val appDAO: AppDAO) : ViewModel() {
    val movieList: MutableLiveData<List<Movie>?> = MutableLiveData()
    val categoryList: MutableLiveData<List<Category>?> = MutableLiveData()
    val UserList: MutableLiveData<List<User>?> = MutableLiveData()
    val seatList: MutableLiveData<List<Seat>?> = MutableLiveData()
    val seatListSelectedFromUser: MutableLiveData<List<Seat>?> = MutableLiveData()
    var currentList: MutableList<Movie> = mutableListOf()
    val list: MutableList<Movie> = mutableListOf(
        Movie(
            movieName = "flash",
            img = R.drawable.movie1,
            img_2 = R.drawable.movie1_2,
            content = "Flash - The Flash (2023) Câu chuyện của Flash bắt đầu khi Barry Allen (Ezra Miller thủ vai) sử dụng siêu năng lực của mình để du hành thời gian nhằm thay đổi những sự kiện trong quá khứ. Nhưng khi nỗ lực cứu lấy gia đình mình vô tình thay đổi tương lai, Barry bị kẹt lại trong một thực tại, nơi tướng Zod tái xuất và đe dọa hủy diệt tất cả, nhưng không có bất cứ siêu anh hùng nào đứng ra giải cứu… Trừ khi Barry có thể thuyết phục một Batman rất khác và giải cứu một cư dân Kryptonian đang bị cầm tù… dẫu có thể đó không phải người mà anh thực sự tìm kiếm. Để giải cứu thế giới hiện tại cũng như trở lại tương lai mình từng biết, niềm hy vọng duy nhất của Barry là phải chạy đua vì cuộc đời mình. Vậy nhưng, sự hy sinh tuyệt đối đó có là đủ để đưa thế giới về lại như ban đầu?",
            nowPlay = true,
            favourite = false,
            vietNamMovie = false,
            popular = false,
            showtime = "23h30"
        ),
        Movie(
            movieName = "Con Tàu Ma",
            img = R.drawable.movie2,
            img_2 = R.drawable.movie2_2,
            content = "Con Tàu Ma - Ghost Ship (2015) Phim Con Tàu Ma: Chiếc thuyền đánh cá lớn và những chàng thủy thủ trôi dạt trong đêm bất ngờ phát hiện 1 xác chết được cho là vợ của tên mafia nắm giữ bến cảng. Kỳ lạ thay, người phụ nữ đó cũng chính là người yêu của Nick – con trai thuyền trưởng. Hàng loạt những sự cố lạ lùng xuất hiện sau đó khiến các thuỷ thủ phải đặt ra câu hỏi: Liệu những gì đang xảy ra có đơn thuần là bị ma ám? Và cái chết này do ai gây ra?",
            nowPlay = true,
            favourite = true,
            vietNamMovie = false,
            popular = false,
            showtime = "9h15p"
        ),
        Movie(
            movieName = "Pháp sư mù",
            img = R.drawable.movie3,
            img_2 = R.drawable.movie3_2,
            content = "Pháp Sư Mù: Ai Chết Giơ tay kể về hành trình đi tìm lại ánh sáng cho đôi mắt của Tinh Lâm (Huỳnh Lập) và những câu chuyện nhân quả đan xen mà Tinh Lâm phải trải qua để thấu hiểu rõ hơn về cuộc đời. Cùng với Thụy Du (Quang Trung) – chàng trai có khả năng cho các linh hồn người chết nhập vào, và Liên Thanh (Hạnh Thảo) – cô gái có khả năng nhìn thấy linh hồn, họ trở thành bộ ba ăn ý. Cả nhóm cùng giải cứu những linh hồn chưa siêu thoát cũng như chữa trị cho đôi mắt của Tinh Lâm.",
            nowPlay = true,
            favourite = false,
            vietNamMovie = true,
            popular = true,
            showtime = "11h15p",
        ),
        Movie(
            movieName = "Người vợ cuối cùng",
            img = R.drawable.movie4,
            img_2 = R.drawable.movie4_2,
            content = "Người vợ cuối cùng :Lấy cảm hứng từ tiểu thuyết Hồ Oán Hận, của nhà văn Hồng Thái, Người Vợ Cuối Cùng là một bộ phim tâm lý cổ trang, lấy bối cảnh Việt Nam vào triều Nguyễn. LINH - Người vợ bất đắc dĩ của một viên quan tri huyện, xuất thân là con của một gia đình nông dân nghèo khó, vì không thể hoàn thành nghĩa vụ sinh con nối dõi nên đã chịu sự chèn ép của những người vợ lớn trong gia đình. Sự gặp gỡ tình cờ của cô và người yêu thời thanh mai trúc mã của mình - NH N đã dẫn đến nhiều câu chuyện bất ngờ xảy ra khiến cuộc sống cô hoàn toàn thay đổi.",
            nowPlay = true,
            favourite = false,
            vietNamMovie = true,
            popular = true,
            showtime = "12h10p"
        ),
        Movie(
            movieName = "Loki",
            img = R.drawable.movie5,
            img_2 = R.drawable.movie5_2,
            content = "Loki: Thần Lừa Lọc Phần 2 - Loki Season 2 (2023) kể về Steve Rogers, Tony Stark và Scott Lang quay trở về cột mốc 2012, ngay khi trận chiến ở New York kết thúc, để “mượn tạm” quyền trượng của Loki. Nhưng một tai nạn bất ngờ xảy đến, khiến Loki nhặt được khối lặp phương Tesseract và tiện thể tẩu thoát. Cuộc trốn thoát này đã dẫn đến dòng thời gian bị rối loạn. Cục TVA – tổ chức bảo vệ tính nguyên vẹn của dòng chảy thời gian, buộc phải can thiệp, đi gô cổ ông thần này về làm việc. Tại đây, Loki có hai lựa chọn, một là giúp TVA ổn định lại thời gian, không thì bị tiêu hủy. Dĩ nhiên Loki chọn lựa chọn thứ nhất. Nhưng đây là nước đi vô cùng mạo hiểm, vì ông thần này thường lọc lừa, “lươn lẹo”, chuyên đâm lén như bản tính tự nhiên của gã.",
            nowPlay = true,
            favourite = true,
            vietNamMovie = false,
            popular = true,
            showtime = "14h15p",
        ),
        Movie(
            movieName = "End Game",
            img = R.drawable.movie6,
            img_2 = R.drawable.movie6_2,
            content = "Avengers: Hồi Kết - Avengers: Endgame (2019) Sau những sự kiện tàn khốc của Avengers: Infinity War (2018), vũ trụ đang bị hủy hoại. Với sự trợ giúp của các đồng minh còn lại, Avengers đã lắp ráp một lần nữa để đảo ngược hành động của Thanos và khôi phục lại sự cân bằng cho vũ trụ.",
            nowPlay = true,
            favourite = true,
            vietNamMovie = false,
            popular = true,
            showtime = "15h45p"
        ),
        Movie(
            movieName = "Toy Story 4",
            img = R.drawable.movie7,
            img_2 = R.drawable.movie7_2,
            content = "Toy story 4 : Khi cô bé Bonnie mang tất cả đám đồ chơi đi du lịch cùng với gia đình mình, Woody đã vô tình hội ngộ với người bạn biệt tích lâu năm, Bo Peep – một búp bê đồ chơi có tinh thần thám hiểm. Khó khăn và hiểm nguy trong chuyến hành trình đã khiến Bo Peep tin rằng mình có thể có một cuộc sống phiêu lưu thú vị hơn là làm một món đồ chơi bằng sứ. Khi Woody và Bo Peep nhận ra họ là những món đồ chơi thuộc về hai thế giới khác nhau, họ đã sớm cảm nhận được những mối lo lắng của mình.",
            nowPlay = true,
            favourite = true,
            vietNamMovie = false,
            popular = false,
            showtime = "17h25p"
        ),
        Movie(
            movieName = "No Way home",
            img = R.drawable.movie8,
            img_2 = R.drawable.movie8_2,
            content = "\n" +"Lần đầu trong lịch sử phim Người Nhện, thân phận của anh hùng khu phô thân thiện bị hé lộ, gây nên mâu thuẫn giữa trách nhiệm siêu anh hùng với cuộc sống thường ngày và khiến những người cậu yêu thương nhất gặp nguy hiểm. Khi cậu nhờ đến sự giúp đỡ của Bác sĩ Strange, câu thần chú tạo ra lỗ hồng trong thế giới của họ, giải phóng những ác nhân hùng mạnh nhất từng chiên đầu với Người Nhện ở mọi vũ trụ. Giờ đây, Peter phải vượt qua thử thách lớn nhất cho đến nay, không chỉ thay đổi tương lai của cậu mà cả tương lai của Đa Vũ Trụ.\n",
            nowPlay = true,
            favourite = true,
            vietNamMovie = false,
            popular = true,
            showtime = "19h05p",
        ),
        Movie(
            movieName = "Săn Quỷ",
            img = R.drawable.movie9,
            img_2 = R.drawable.movie9_2,
            content = "\n" +"Nghệ Thuật Săn Quỷ Và Nấu Mì - The Uncanny Counter (2020) Ban ngày là nhân viên tiệm mì, ban đêm là thợ săn quỷ, họ dùng những khả năng đặc biệt để truy lùng lũ ma quỷ độc ác chuyên tấn công con người.",
            nowPlay = true,
            favourite = true,
            vietNamMovie = false,
            popular = false,
            showtime = "20h35p",
        ),
        Movie(
            movieName = "Luca",
            img = R.drawable.movie10,
            img_2 = R.drawable.movie10_2,
            content = "\n" +"Mùa Hè Của Luca kể về Luca Paguro, một thủy quái trẻ sống ngoài khơi Ý những năm 1950-1960. Gặp Alberto, họ khám phá thế giới trên bề mặt và tham gia cuộc đua Vespa ở Portorosso với Giulia. Đối mặt với sự hiểu lầm và thách thức gia đình, Luca học về tình bạn, lòng dũng cảm và chấp nhận sự khác biệt trong cuộc phiêu lưu của mình. ",
            nowPlay = true,
            favourite = true,
            vietNamMovie = false,
            popular = false,
            showtime = "22h25p",
        ),
    )

    val currenlistSeat: List<Seat> = listOf(
        Seat(
            movieName = "flash",
            checkSeat = false,
            isSeat = false,
            img = R.drawable.img_seat_reserved,
            showtime = "7h15p",
        ),
        Seat(
            movieName = "flash",
            checkSeat = false,
            isSeat = false,
            img = R.drawable.img_seat_reserved,
            showtime = "7h15p"
        ),
        Seat(
            movieName = "flash",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "7h15p"
        ),
        Seat(
            movieName = "flash",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "7h15p"
        ),
        Seat(
            movieName = "flash",
            checkSeat = false,
            isSeat = false,
            img = R.drawable.img_seat_reserved,
            showtime = "7h15p"
        ),

        Seat(
            movieName = "flash",
            checkSeat = false,
            isSeat = false,
            img = R.drawable.img_seat_reserved,
            showtime = "7h15p"
        ),
        Seat(
            movieName = "flash",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "7h15p"
        ),
        Seat(
            movieName = "flash",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "7h15p"
        ),
        Seat(
            movieName = "flash",
            checkSeat = false,
            isSeat = false,
            img = R.drawable.img_seat_reserved,
            showtime = "7h15p"
        ),
        Seat(
            movieName = "flash",
            checkSeat = false,
            isSeat = false,
            img = R.drawable.img_seat_reserved,
            showtime = "7h15p"
        ),
        Seat(
            movieName = "flash",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "7h15p"
        ),
        Seat(
            movieName = "flash",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "7h15p"
        ),
        Seat(
            movieName = "Con Tàu Ma",
            checkSeat = false,
            isSeat = false,
            img = R.drawable.img_seat_reserved,
            showtime = "9h15p",
        ),
        Seat(
            movieName = "Con Tàu Ma",
            checkSeat = false,
            isSeat = false,
            img = R.drawable.img_seat_reserved,
            showtime = "9h15p",
        ),
        Seat(
            movieName = "Con Tàu Ma",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "9h15p",
        ),
        Seat(
            movieName = "Con Tàu Ma",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "9h15p",
        ),
        Seat(
            movieName = "Con Tàu Ma",
            checkSeat = false,
            isSeat = false,
            img = R.drawable.img_seat_reserved,
            showtime = "9h15p",
        ),
        Seat(
            movieName = "Con Tàu Ma",
            checkSeat = false,
            isSeat = false,
            img = R.drawable.img_seat_reserved,
            showtime = "9h15p",
        ),
        Seat(
            movieName = "Con Tàu Ma",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "9h15p",
        ),
        Seat(
            movieName = "Con Tàu Ma",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "9h15p",
        ),
        Seat(
            movieName = "Con Tàu Ma",
            checkSeat = false,
            isSeat = false,
            img = R.drawable.img_seat_reserved,
            showtime = "9h15p",
        ),
        Seat(
            movieName = "Con Tàu Ma",
            checkSeat = false,
            isSeat = false,
            img = R.drawable.img_seat_reserved,
            showtime = "9h15p",
        ),
        Seat(
            movieName = "Con Tàu Ma",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "9h15p",
        ),
        Seat(
            movieName = "Con Tàu Ma",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "9h15p",
        ),
        Seat(
            movieName = "Pháp sư mù",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "11h15p",
        ),
        Seat(
            movieName = "Pháp sư mù",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "11h15p",
        ),
        Seat(
            movieName = "Pháp sư mù",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "11h15p",
        ),
        Seat(
            movieName = "Pháp sư mù",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "11h15p",
        ),
        Seat(
            movieName = "Pháp sư mù",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "11h15p",
        ),
        Seat(
            movieName = "Pháp sư mù",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "11h15p",
        ),
        Seat(
            movieName = "Pháp sư mù",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "11h15p",
        ),
        Seat(
            movieName = "Pháp sư mù",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "11h15p",
        ),
        Seat(
            movieName = "Pháp sư mù",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "11h15p",
        ),
        Seat(
            movieName = "Pháp sư mù",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "11h15p",
        ),
        Seat(
            movieName = "Pháp sư mù",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "11h15p",
        ),
        Seat(
            movieName = "Pháp sư mù",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "11h15p",
        ),
        Seat(
            movieName = "Người vợ cuối cùng",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "12h10p",

            ),
        Seat(
            movieName = "Người vợ cuối cùng",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "12h10p",
        ),
        Seat(
            movieName = "Người vợ cuối cùng",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "12h10p",
        ),
        Seat(
            movieName = "Người vợ cuối cùng",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "12h10p",
        ),
        Seat(
            movieName = "Người vợ cuối cùng",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "12h10p",
        ),
        Seat(
            movieName = "Người vợ cuối cùng",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "12h10p",
        ),
        Seat(
            movieName = "Người vợ cuối cùng",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "12h10p",
        ),
        Seat(
            movieName = "Người vợ cuối cùng",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "12h10p",
        ),
        Seat(
            movieName = "Người vợ cuối cùng",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "12h10p",
        ),
        Seat(
            movieName = "Người vợ cuối cùng",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "12h10p",
        ),
        Seat(
            movieName = "Người vợ cuối cùng",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "12h10p",
        ),
        Seat(
            movieName = "Người vợ cuối cùng",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "12h10p",
        ),
        Seat(
            movieName = "Loki",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "14h15p",
        ),
        Seat(
            movieName = "Loki",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "14h15p",
        ),
        Seat(
            movieName = "Loki",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "14h15p",
        ),
        Seat(
            movieName = "Loki",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "14h15p",
        ),
        Seat(
            movieName = "Loki",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "14h15p",
        ),
        Seat(
            movieName = "Loki",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "14h15p",
        ),
        Seat(
            movieName = "Loki",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "14h15p",
        ),
        Seat(
            movieName = "Loki",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "14h15p",
        ),
        Seat(
            movieName = "Loki",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "14h15p",
        ),
        Seat(
            movieName = "Loki",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "14h15p",
        ),
        Seat(
            movieName = "Loki",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "14h15p",
        ),
        Seat(
            movieName = "Loki",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "14h15p",
        ),
        Seat(
            movieName = "End Game",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "15h45p",
        ),
        Seat(
            movieName = "End Game",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "15h45p",
        ),
        Seat(
            movieName = "End Game",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "15h45p",
        ),
        Seat(
            movieName = "End Game",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "15h45p",
        ),
        Seat(
            movieName = "End Game",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "15h45p",
        ),
        Seat(
            movieName = "End Game",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "15h45p",
        ),
        Seat(
            movieName = "End Game",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "15h45p",
        ),
        Seat(
            movieName = "End Game",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "15h45p",
        ),
        Seat(
            movieName = "End Game",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "15h45p",
        ),
        Seat(
            movieName = "End Game",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "15h45p",
        ),
        Seat(
            movieName = "End Game",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "15h45p",
        ),
        Seat(
            movieName = "End Game",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "15h45p",
        ),
        Seat(
            movieName = "Toy Story 4",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "17h25p",
        ),
        Seat(
            movieName = "Toy Story 4",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "17h25p",
        ),
        Seat(
            movieName = "Toy Story 4",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "17h25p",
        ),
        Seat(
            movieName = "Toy Story 4",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "17h25p",
        ),
        Seat(
            movieName = "Toy Story 4",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "17h25p",
        ),
        Seat(
            movieName = "Toy Story 4",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "17h25p",
        ),
        Seat(
            movieName = "Toy Story 4",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "17h25p",
        ),
        Seat(
            movieName = "Toy Story 4",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "17h25p",
        ),
        Seat(
            movieName = "Toy Story 4",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "17h25p",
        ),
        Seat(
            movieName = "Toy Story 4",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "17h25p",
        ),
        Seat(
            movieName = "Toy Story 4",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "17h25p",
        ),
        Seat(
            movieName = "Toy Story 4",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "17h25p",
        ),

        Seat(
            movieName = "No Way home",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "19h05p",
        ),
        Seat(
            movieName = "No Way home",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "19h05p",
        ),
        Seat(
            movieName = "No Way home",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "19h05p",
        ),
        Seat(
            movieName = "No Way home",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "19h05p",
        ),
        Seat(
            movieName = "No Way home",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "19h05p",
        ),
        Seat(
            movieName = "No Way home",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "19h05p",
        ),
        Seat(
            movieName = "No Way home",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "19h05p",
        ),
        Seat(
            movieName = "No Way home",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "19h05p",
        ),
        Seat(
            movieName = "No Way home",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "19h05p",
        ),
        Seat(
            movieName = "No Way home",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "19h05p",
        ),
        Seat(
            movieName = "No Way home",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "19h05p",
        ),
        Seat(
            movieName = "No Way home",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "19h05p",
        ),
        Seat(
            movieName = "Săn Quỷ",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "20h35p",
        ),
        Seat(
            movieName = "Săn Quỷ",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "20h35p",
        ),
        Seat(
            movieName = "Săn Quỷ",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "20h35p",
        ),
        Seat(
            movieName = "Săn Quỷ",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "20h35p",
        ),
        Seat(
            movieName = "Săn Quỷ",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "20h35p",
        ),
        Seat(
            movieName = "Săn Quỷ",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "20h35p",
        ),
        Seat(
            movieName = "Săn Quỷ",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "20h35p",
        ),
        Seat(
            movieName = "Săn Quỷ",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "20h35p",
        ),
        Seat(
            movieName = "Săn Quỷ",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "20h35p",
        ),
        Seat(
            movieName = "Săn Quỷ",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "20h35p",
        ),
        Seat(
            movieName = "Săn Quỷ",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "20h35p",
        ),
        Seat(
            movieName = "Săn Quỷ",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "20h35p",
        ),
        Seat(
            movieName = "Săn Quỷ",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "20h35p",
        ),
        Seat(
            movieName = "Luca",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "22h25p",
        ),
        Seat(
            movieName = "Luca",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "22h25p",
        ),
        Seat(
            movieName = "Luca",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "22h25p",
        ),
        Seat(
            movieName = "Luca",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "22h25p",
        ),
        Seat(
            movieName = "Luca",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "22h25p",
        ),
        Seat(
            movieName = "Luca",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "22h25p",
        ),
        Seat(
            movieName = "Luca",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "22h25p",
        ),
        Seat(
            movieName = "Luca",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "22h25p",
        ),
        Seat(
            movieName = "Luca",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "22h25p",
        ),
        Seat(
            movieName = "Luca",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "22h25p",
        ),
        Seat(
            movieName = "Luca",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "22h25p",
        ),
        Seat(
            movieName = "Luca",
            checkSeat = false,
            img = R.drawable.img_seat_reserved,
            isSeat = false,
            showtime = "22h25p",
        ),
    )


    val listCategory: List<Category> = listOf(
        Category(categoryMovie = "Now Play"),
        Category(categoryMovie = "favourite"),
        Category(categoryMovie = "VNM"),
        Category(categoryMovie = "Popular"),
    )

    fun insertMovieList() {
        viewModelScope.launch(Dispatchers.IO) {
            appDAO.insertMovieList(list)
            withContext(Dispatchers.Main) {
                getMovieList()
            }
        }
    }

   

    fun getMovieList() {
        viewModelScope.launch(Dispatchers.IO) {
            val listMovie = appDAO.getMovieList()
            withContext(Dispatchers.Main) {
                if (listMovie.isNullOrEmpty()) {
                    insertMovieList()
                } else {
                    movieList.value = listMovie
                }

            }
        }
    }

    fun insertCategoryList() {
        viewModelScope.launch(Dispatchers.IO) {
            appDAO.insertListCategory(listCategory)
            withContext(Dispatchers.Main) {
                getCategoryList()
            }
        }
    }

    fun getCategoryList() {
        viewModelScope.launch(Dispatchers.IO) {
            val listCategory = appDAO.getCategory()
            withContext(Dispatchers.Main) {
                categoryList.value = listCategory
            }
        }
    }

    fun getUserList() {
        viewModelScope.launch(Dispatchers.IO) {
            val User = appDAO.getUserList()
            withContext(Dispatchers.Main) {
                UserList.value = User
            }
        }
    }

    fun deleteSeat(movieName: String, seatNumber: Int, emailName: String, seat: Seat) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = appDAO.getUser(emailName)
            val movie = appDAO.getMovie(movieName)
            val newSeat: Seat = Seat(
                    seatNumber = seatNumber,
                    movieName = movieName,
                    checkSeat = false,
                    isSeat = false,
                    img = R.drawable.img_seat_reserved,
                    showtime = movie?.showtime,
                )
            var revenue = movie?.revenue?.minus(50000.0)
            var totalSpending = user?.totalSpending?.minus(50000.0)

            if (revenue != null) {
                Log.d("deleteSeat", revenue.toString())
                appDAO.updateRevenueMovie(revenue, movieName)
            }
            if (user != null) {
                Log.d("deleteSeat", totalSpending.toString())
                appDAO.updatetotalSpendingUser(totalSpending, emailName)
            }


            appDAO.deleteSeat(movieName,seat.seatNumber)
            appDAO.insertSeat(newSeat)

            withContext(Dispatchers.Main) {
                getSeatListSelecterFromUser(emailName)
            }
        }
    }

    fun deleteUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            val listMovie = appDAO.getMovieList()
            var revenue: Double = 0.0
            var totalSpenDing: Double = 0.0
            var totalSpenDingUpdate: Double = 0.0
            if (listMovie != null) {
                for (i in 0 until listMovie.size) {
                    val movie = listMovie[i]
                    val listSeatSelectFromUser =
                        appDAO.getListSeatWhereMovieUser(
                            movie.movieName.toString(),
                            user.emailName.toString(),
                            true
                        )
                    if (listSeatSelectFromUser != null) {
                        for (i in 0 until listSeatSelectFromUser.size) {
                            val seatSelect = listSeatSelectFromUser[i]
                            appDAO.updateCheckSeatFromAdmin(false, seatSelect.user.toString())
                            if (listSeatSelectFromUser != null) {
                                revenue =
                                    (movie.revenue?.minus(listSeatSelectFromUser.size * 50000.0)
                                        ?: Double) as Double
                                Log.d("revenue", movie.movieName + revenue.toString())
                                totalSpenDingUpdate =
                                    totalSpenDing - listSeatSelectFromUser.size * 50000.0
                            }
                        }
                    }
                    appDAO.updateRevenueMovie(revenue, movie.movieName.toString())
                    appDAO.updatetotalSpendingUser(totalSpenDingUpdate, user.emailName.toString())
                }
            }
            appDAO.deleteUser(user)
            withContext(Dispatchers.Main) {
                getUserList()
            }
        }
    }

    fun CurrentInsertSeatList(movie: String) {
        viewModelScope.launch(Dispatchers.IO) {
            appDAO.insertListSeat(currenlistSeat)
            withContext(Dispatchers.Main) {
                getSeatList(movie)
            }
        }
    }

    fun updataMoneyUser(spending: Double,userName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = appDAO.getUser(userName)
            val money = user?.money?.minus(spending)
            appDAO.updateMoneyUser(money,userName)
        }
    }


    fun UpdateListCheckSeat(
        userBuyticket: String, listSeatCheck: MutableList<Seat>, movieName: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            var x: Double = 0.0
            // tính tiền hóa đơn
            for (i in 0 until listSeatCheck.size) {
                x += 50000
                val seatCheck = listSeatCheck[i]
                appDAO.updateCheckSeat(
                    user = userBuyticket,
                    checkSeat = seatCheck.checkSeat,
                    seatNumber = seatCheck.seatNumber,
                    movieName = seatCheck.movieName.toString()
                )
            }

            // update revenue của phim trong room dataBase
            val movie = appDAO.getMovie(movieName)
            val updateRevenue = movie?.revenue?.plus(x)
            if (updateRevenue != null) {
                appDAO.updateRevenueMovie(updateRevenue, movieName)
            }

            // update Tổng chi tiêu
            var updateTotalSpending:Double? = 0.0
            val user = appDAO.getUser(userBuyticket)
            if (user != null) {
                updateTotalSpending = user.totalSpending.plus(x)

            }

            if (updateTotalSpending != null){
                appDAO.updatetotalSpendingUser(updateTotalSpending, userBuyticket)
            }


            withContext(Dispatchers.Main) {
                listSeatCheck.get(0).movieName?.let { getSeatList(it) }
            }
        }
    }

    fun getSeatList(movie: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val listSeat = appDAO.getListSeatFromMovie(movie)
            withContext(Dispatchers.Main) {
                seatList.value = listSeat
            }
        }
    }


    fun getSeatListSelecterFromUser(user: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val seatListfromUser = appDAO.getListSeatSelectFromUser(user, true)
            withContext(Dispatchers.Main) {
                seatListSelectedFromUser.value = seatListfromUser
            }
        }
    }

    fun faceListByCategory(category: Category) {
        currentList = mutableListOf()
        when (category.categoryMovie) {
            "favourite" -> {
                currentList.addAll(list.filter { it.favourite == true })
            }
            "Now Play" -> {
                currentList.addAll(list.filter { it.nowPlay == true })
            }
            "VNM" -> {
                currentList.addAll(list.filter { it.vietNamMovie == true })
            }
            else -> {
                currentList.addAll(list.filter { it.popular == true })
            }
        }
        movieList.value = currentList

    }
    fun resetEndDay(){
        viewModelScope.launch(Dispatchers.IO) {
            appDAO.updateSeat(false,"")
        }
    }
    
    fun resetEndMonth() {
        viewModelScope.launch(Dispatchers.IO) {
            appDAO.updateSeat(false,"")
            appDAO.updateRevenue(0.0)
        }
    }
}

