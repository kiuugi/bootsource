// 날짜 포멧 함수
const formateDate = (data) => {
  const date = new Date(data);
  return date.getFullYear() + "/" + (date.getMonth() + 1) + "/" + date.getDate() + " " + date.getHours() + ":" + date.getMinutes();
};

// 화면이 로드되면 바로 리뷰 보여주기
// /review/{}/all
const reviewsLoaded = () => {
  fetch(`/reviews/${mno}/all`, { method: "get" })
    .then((response) => response.json())
    .then((data) => {
      console.log(data);

      let result = "";
      data.forEach((review) => {
        result += `<div class="d-flex justify-content-between my-2 border-bottom py-2 review-row" data-rno="1">`;
        result += `<div class="flex-grow-1 align-self-center">`;
        result += `<div><span class="font-semibold" >${review.text}</span></div>`;
        result += `<div class="small text-muted"><span class="d-inline-block mr-3">${review.nickname}</span>`;
        result += `평점 : <span class="grade">${review.grade} </span></div>`;
        result += `<div class="text-muted"><span class="small">${formateDate(review.lastModifiedDate)}</span></div></div>`;
        result += `<div class="d-flex flex-column align-self-center"><div class="mb-2">`;
        // if (`${mid}` == `${review.mid}`) {
        result += '<button class="btn btn-outline-danger btn-sm">삭제</button></div>';
        result += '<div><button class="btn btn-outline-success btn-sm">수정</button></div></div></div>';
        // }
      });
      reviewList.innerHTML = result;
    });
};

reviewsLoaded(); // 나중에 댓글을 수정했을때 바로바로 업데이트해주기위해 함수로 만들어둠

// 작은 포스터 클릭하면 큰 포스터 보여주시
const imgModal = document.getElementById("imgModal");

if (imgModal) {
  imgModal.addEventListener("show.bs.modal", (e) => {
    // 모달을 뜨게 만든 li 가져오기
    const posterLi = e.relatedTarget;

    // data- : dataset
    const file = posterLi.getAttribute("data-file");
    console.log("file", file);

    // 타이틀 영역 영화면 삽입
    imgModal.querySelector(".modal-title").textContent = `${title}`;
    // 이미지 경로 변경
    const modalBody = imgModal.querySelector(".modal-body");
    modalBody.innerHTML = `<img src='/upload/display?fileName=${file}' style='width: 100%' >`;
  });
}

// const posterLi = document.querySelector(".uploadResult li");
// posterLi.array.forEach((poster) => {
//   poster.addEventListener("click", () => {
//     imgModal.show();
//   });
// });
