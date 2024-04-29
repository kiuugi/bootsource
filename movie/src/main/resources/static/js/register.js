// form submit 기능 중지
// uploadResult ul li 태그 요소 가져오기
document.querySelector("#register-form").addEventListener("submit", (e) => {
  e.preventDefault();
  const form = e.target;
  // 첨부파일 정보 수집
  const attachInfos = document.querySelectorAll(".uploadResult ul li");
  console.log(attachInfos);

  // 수집된 정보를 폼 태그 자식으로 붙여넣기
  let result = "";
  attachInfos.forEach((obj, idx) => {
    // hidden 3개 => MovieImageDto 객체 하나로 변경(spring에서 대입해줌 알려주기만 하면 됨) list이기 때문에 []까지 입력
    result += `<input type='hidden' value='${obj.dataset.path}' name='movieImageDtos[${idx}].path' >`;
    result += `<input type='hidden' value='${obj.dataset.uuid}' name='movieImageDtos[${idx}].uuid' >`;
    result += `<input type='hidden' value='${obj.dataset.name}' name='movieImageDtos[${idx}].imgname' >`;
  });
  form.insertAdjacentHTML("beforeend", result);

  console.log(form);

  form.submit();
});

// x 를 누르면 파일 삭제 요청
// a 태그 기능 중지 => href 값 가져와서 화면 출력
document.querySelector(".uploadResult").addEventListener("click", (e) => {
  e.preventDefault();

  console.log("x 클릭" + e.target); // <i class="fa-solid fa-xmark"></i>
  console.log(e.currentTarget); // output에 있는 애들 전부를 다 가져옴
  const currentLi = e.target.closest("li");
  // data-file 에 있는 값 가져오기
  const filePath = e.target.closest("a").dataset.file; // data안에 들어있는거 가져올때 무조건 set
  console.log("filePath", filePath);

  const formData = new FormData();
  formData.append("filePath", filePath);

  fetch("/upload/remove", { method: "post", body: formData })
    .then((response) => response.text())
    .then((data) => {
      console.log(data);
      if (data) {
        // 해당 div 제거
        currentLi.remove();
      }
    });
});
