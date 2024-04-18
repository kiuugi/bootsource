const actionForm = document.querySelector("#actionForm");
document.querySelector(".btn-danger").addEventListener("click", (e) => {
  // e.preventDefault; 이거 어차피 submit 아니라서 form 발동안함
  if (!confirm("정말로 삭제하시겠습니다?")) {
    return;
  }
  actionForm.action = "/board/delete";
  actionForm.submit();
});
