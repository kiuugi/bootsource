// 제목 클릭 시 a 태그 기능 중지
// data-id 에 있는 값 가져오기
document.querySelectorAll("tbody a").forEach((link) => {
  link.addEventListener("click", (e) => {
    e.preventDefault();
    const target = e.target;

    console.log(target.dataset.id); // 332 id값이 넘어옴

    //   const dataId = document.querySelector("[data-id]");
    fetch(`http://localhost:8080/read/${target.dataset.id}`)
      .then((resone) => resone.json())
      .then((data) => {
        console.log(data); // id 332 객체가 들어옴

        // 디자인 영역 가져오기
        document.querySelector("#category").value = data.categoryName;
        document.querySelector("#title").value = data.title;
        document.querySelector("#publisher").value = data.publisherName;
        document.querySelector("#writer").value = data.writer;
        document.querySelector("#price").value = data.price;
        document.querySelector("#salePrice").value = data.salePrice;
        document.querySelector("#book_id").value = data.id;
      });
  });
});

// 삭제 클릭 시 id 가져오기
document.querySelector(".btn-danger").addEventListener("click", (e) => {
  e.preventDefault();
  const id = document.querySelector("#book_id").value; // 위에서 뿌려준 data.id가 book_id의 value에 담김
  console.log("삭제 클릭했을때 가져온 id " + id); // 412
  fetch(`/delete/${id}`, {
    method: "delete", // @DeleteMapping("/delete/{id}") 지금 이걸 받는 메소드가 delete로 맵핑을 잡아놈
  })
    .then((response) => response.text())
    .then((data) => {
      if (data == "success") {
        alert("삭제 성공");
        location.href = "/book/list";
      }
    });
});

document.querySelector(".btn-warning").addEventListener("click", (e) => {
  e.preventDefault();

  // form 내용 가져오기 => javascript 객체 생성
  // const myForm = document.querySelector("#myForm");
  // myForm 안에 들어있는 요소 찾기
  // myForm.querySelector("")
  const book_id = document.querySelector("#book_id").value;
  const data = {
    id: book_id,
    title: document.querySelector("#title").value,
    writer: document.querySelector("#writer").value,
    price: document.querySelector("#price").value,
    salePrice: document.querySelector("#salePrice").value,
  };
  console.log(data);
  // method 지정 안하면 get방식으로 전송됨
  fetch(`/modify/${book_id}`, {
    method: "put",
    headers: { "content-type": "application/json" },
    body: JSON.stringify(data), //JSON.stringify javascript 객체 -> json 타입으로 변환
  })
    .then((response) => response.text())
    .then((data) => {
      if (data == "success") {
        alert("수정 성공");
        location.href = "/book/list?page=1&type=&keyword=";
      }
    });
});
