document.querySelector("#searchForm").addEventListener("submit", (e) => {
  e.preventDefault;
  const type = document.querySelector("#type");
  const keyword = document.querySelector("#keyword");
  const searchForm = document.querySelector("#searchForm");
  if (type.value == "") {
    alert("조건을 확인해주세요");
    return;
  } else if (keyword.value == "") {
    alert("검색어를 확인해주세요");
    return;
  }
  searchForm.action = "/guestbook/list";
  searchForm.submit();
});
