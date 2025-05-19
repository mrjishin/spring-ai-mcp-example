const initTableCheck = (name) => {
  const tid = name.startsWith('#') ? name : '#' + name;
  let oTable = document.querySelector(tid);
  const oChkAll = oTable.querySelector('thead').querySelector('input[type=checkbox]');
  const oChks = oTable.querySelector('tbody').querySelectorAll('input[type=checkbox]');
  oChkAll.addEventListener('change', (event) => {
    oChks.forEach((c) => c.checked = event.target.checked);
  });
  oChks.forEach((c) => {
    c.addEventListener('change', () => {
      let checkedCnt = 0;
      oChks.forEach((c2) => {
        if(c2.checked)
          checkedCnt++;
      });
      oChkAll.checked = checkedCnt > 0;
    });
  });
}