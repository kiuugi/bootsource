// 삭제버튼 클릭시 actionForm 전송
const form = document.querySelector("#actionForm");
document.querySelector(".btn-danger").addEventListener("click", (e) => {
  if (!confirm("정말로 삭제하시겠습니다?")) {
    return;
  }
  form.action = "/book/delete";
  form.submit();
});
