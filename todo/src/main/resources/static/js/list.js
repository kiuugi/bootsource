//체크박스 클릭시 id 가져오기

// 화면의 중복 요소에 이벤트 작성
// document.querySelectorAll("[name='completed']").addEventListener("click", (e) => {});

// 이벤트 전파 => 부모 요소가 감지
document.querySelector(".list-group").addEventListener("click", (e) => {
  console.log("이벤트가 발생한 대상" + e.target);
  console.log("이벤트를 감지한 대상" + e.currentTarget);
  console.log("이벤트가 발생한 대상의 value값" + e.target.value);

  //   location.href = "/todo/update?id=" + e.target.value;
  //   fetch, form을 써서 post 방식으로 보내기
  const form = document.querySelector("#completedForm");
  form.querySelector('[name="id"]').value = e.target.value;
  form.submit();
});
