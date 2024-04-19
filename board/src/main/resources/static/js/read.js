// 페이지 로드시 무조건 실행
// 즉시실행함수, 일반함수
// fetch() - 함수 작성 후 호출

// 함수작성
// 1. function 함수명(){}
// 2. const(let) 이름 = ()=>{}

// 함수 실행 => 호출
// 함수명(); , 이름();

// 호이스팅 (선언 안하고 먼저 호출 후 선언)
// 1번은 호이스팅 가능, 2번은 불가능

// var 로 선언된 변수는 호이스팅 됨
// const, let은 호이스팅 안됨

// 날짜 포맷 변경 함수
const formateDate = (data) => {
  const date = new Date(data);

  return date.getFullYear() + "/" + (date.getMonth() + 1) + "/" + date.getDate() + " " + date.getHours() + ":" + date.getMinutes();
};

// 댓글 목록 가져오기
const replyList = () => {
  // 댓글 목록 보여줄 영역 가져오기
  const replyList = document.querySelector(".replyList");
  // html의 script 에 정의되었는 bno를 씀
  fetch(`/replies/board/${bno}`)
    .then((response) => response.json())
    .then((data) => {
      console.log(data);

      document.querySelector(".d-inline-block").innerHTML = data.length;

      let result = "";
      data.forEach((reply) => {
        // 영역에 result 보여주기
        result += `<div class="d-flex justify-content-between my-2 border-bottom reply-row" data-rno="${reply.rno}">`;
        result += `<div class="p-3"><img src="/img/default.png" alt="" class="reounded-circle mx-auto d-block" style="width: 60px; height: 60px" /></div>`;
        result += `<div class="flex-grow-1 align-self-center"><div>${reply.replyer}</div>`;
        result += `<div><span class="fs-5">${reply.text}</span></div>`;
        result += `<div class="text-muted"><span class="small">${formateDate(reply.createdDate)}</span></div></div>`;
        result += `<div class="d-flex flex-column align-self-center"><div class="mb-2"><button class="btn btn-outline-danger bth-sm">삭제</button></div>`;
        result += `<div><button class="btn btn-outline-success bth-sm">수정</button></div>`;
        result += `</div></div>`;
      });
      replyList.innerHTML = result;
    });
};

replyList();

// 새 댓글 등록
// 등록 버튼 submit 시
// submit 기능 중지/ 작성자 / 댓글 가져오기 => 스크립트 객체로 변경
const replyForm = document.querySelector("#replyForm");
replyForm.addEventListener("submit", (e) => {
  e.preventDefault();

  const replyer = document.querySelector("#replyer");
  const text = document.querySelector("#text");

  const data = {
    replyer: replyer.value,
    text: text.value,
    boardId: bno,
  };
  console.log(data);

  fetch(`/replies/new`, {
    method: "post",
    headers: { "content-type": "application/json" },
    body: JSON.stringify(data),
  })
    .then((response) => response.text())
    .then((data) => {
      if (data) {
        alert(data + "번 댓글을" + bno + "번 글에 댓글 등록");
        //repltForm 내용 제거
        replyer.value = "";
        text.value = "";
        replyList();
      }
    });
});

// 댓글 삭제 버튼 클릭 시 fetch(비동기) 방식으로 오는 html 요소는 바로 접근이 불가능함 에초에 안됨
// rno 가져오기
// document.querySelectorAll(".btn-outline-danger").forEach((link) => {
//   link.addEventListener("click", (e) => {
//     console.log("삭제클릭");
//     const rno = document.querySelector(".justify-content-between").dataset.rno;
//     console.log(rno);
//   });
// });

// 이벤트 전파로 찾아오기 - 실제 이벤트가 일어난 대상은 누구인가
document.querySelector(".replyList").addEventListener("click", (e) => {
  const btn = e.target;

  // closest("요소") - 가장 가까운 상위요소 찾기
  const rno = btn.closest(".reply-row").dataset.rno; // data-* 로 작성된 값 불러오기 dataset.*
  console.log("rno", rno);
  //   document.querySelectorAll(".btn-outline-danger").forEach((link) => { 이렇게 부모를 찾아놓은 상태로 fatch방식으로 넘어온 html을 찾으면 찾아지긴함
  //     link.addEventListener("click", (e) => {
  //       console.log("삭제클릭");
  //       const rno = document.querySelector(".justify-content-between").dataset.rno;
  //       console.log(rno);
  //     });
  //   });
  fetch(`/replies/${rno}`, { method: "delete" })
    .then((response) => response.text())
    .then((data) => {
      if (data == "success") {
        alert("댓글 삭제 성공");
        replyList();
      }
    });
});
