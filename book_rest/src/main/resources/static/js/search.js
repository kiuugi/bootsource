// modify.js 처럼 버튼 클릭시 멈추고 searchForm submit 해줘도 똑같음
const searchForm = document.querySelector("#searchForm").addEventListener("submit", (e) => {
  e.preventDefault;

  const type = document.querySelector("#type");
  const keyword = document.querySelector("#keyword");

  if (type.value == "") {
    alert("검색 타입을 확인해 주세요");
    type.focus();
    return;
  } else if (keyword.value == "") {
    alert("검색어를 확인해 주세요");
    keyword.focus();
    return;
  }

  e.target.submit();
});
