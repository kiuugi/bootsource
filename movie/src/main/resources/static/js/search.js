// 검색창에서 엔터를 치면 이벤트 발생
// 검색어 가지고 온 후 searchForm 안 keyword 요소 value 값 지정
// searchForm 전송
const searchForm = document.querySelector("#searchForm");

document.querySelector("[name=keyword]").addEventListener("keyup", (e) => {
  if (e.keyCode == 13) {
    // 13번 key가 enter인듯
    const keyword = e.target.value;
    if (!keyword) {
      alert("검색어를 확인해주세요");
      return;
    }
    searchForm.querySelector("[name=keyword]").value = keyword;
    console.log(searchForm);
    searchForm.submit();
  }
});
