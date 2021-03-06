// 이미지 미리보기
function imgUpload() {
   // 파일 업로드 태그 >> 파일 업로드시 해당 파일을 가지고 있는 객체이다.
   let upload = document.querySelector("#userImgFile") 
   // 추가될 이미지 영역 
   let preview = document.querySelector("#imgtd") 

   
   /* #1 자바스트립트의 FileReader 객체 생성 */
   let reader = new FileReader()
   console.log(1)
   
   /* #2 addEventListener를 사용하여 change 이벤트 적용  */
   upload.addEventListener('change', function(e) {
      
      /* #3 이벤트를 발생시킨 타겟의 파일객체를 가져온다(배열)  */
      let get_file = e.target.files
      console.log(2)
      //자바스크립트에서 올리는 파일이름 + 확장자 
      var fileName = e.target.files[0].name;
      
      //fileName을 보내서 폴더안의 이름과 비교하고자 했으나, 회원마다 폴더를 생성해야하는 불상사.. 발생 우선 보류 
      //document.querySelector('#sduimgpath').value = fileName;
      
      if (get_file) { // 자바스크립트의 undefined는 false를 의미 즉 값이 있으면 true
         /* #4 - 2 */
         reader.readAsDataURL(get_file[0]);
         /*
           # readAsDataURL(파일)     
           >>> FileReader객체의 함수로 파일의 읽어 base64 스트링을 받아오는 메서드이다.  
           1) 읽어오는 read 행위가 종료되는 경우에, readyState 의 상태가 DONE이 됨   
           2) loadend 이벤트가 트리거 됩니다. 
           3) 이와 함께, base64 인코딩 된 스트링 데이터가 result 속성(attribute)에 담아지게 됩니다.
          */
         
         console.log(4 + " : 파일 읽기 완료")
      }
   })
   
   /* #4 - 1 reader 파일을 읽기 시작하면 시작시 함수 구현 */
   reader.onload = (function() {
      console.log(3 + " : 파일 읽기 시작")
      // 새로운 img태그
      let profileImg = document.createElement("img")
      profileImg.setAttribute("id", "userImg")
      profileImg.setAttribute("style", "width:300px;")
      
      /* #5 파일을 다 읽었을때 loadend 이벤트가 발생 >>> 이후 return 발생한다. */
      return function(event) {
         console.log(5 + " : 파일 읽기 완료후 이벤트")
         /* #7 base64 인코딩 된 스트링 데이터 == result */
         profileImg.src = event.target.result   // event.target = loadend 함수가 발생시킨 이벤트

         preview.innerHTML = "";
         preview.appendChild(profileImg);
         
         
         
        //#sduimgpath의 값을 항상 null로 주고 어떤 값이 되어도 업데이트를 할 수 있게 한다. 
        document.querySelector('#sduimgpath').value = "";
         
         console.log(6 + " : 완료")
      }
   })()
}

/*
 * # "EventTarget.addEventListener('이벤트 type', 이벤트)"에 대하여 
  
    > 사용법 : 
   var taget = document.querySelector("#taget") 
   
   // 1) 함수를 구현하여 이벤트 적용 
   function myEvent(event){ 
      console.log("event를 파라미터로 받는 함수를 구현") 
   }
    taget.addEventListener('click', myEvent) 
    
    
    // 2) 익명함수를 사용하여 이벤트 적용
    taget.addEventListener('이벤트 type', function(event){ 
    console.log("event를 파라미터로 받는 익명함수를 사용해 이벤트 구현")
    }) 
    
    > 기존방식과 다른 장점 : 
   기존 태그에 onclick=""에 함수를 등록하거나 window.onload를 이용하여 등록할 경우 
   하나의 타겟에 중복되는 이벤트 적용시 기존 적용된 이벤트는 제거하고 새로 적용한 이벤트만 적용되었음 
   하지만 addEventListener을 사용시에는 여러이벤트를 적용시 모두 적용되며 순차적으로 실행이 된다.
   즉, 하나의 태그에 여러함수를 적용할 수 있다.
   
   
   > 응용한 사용법 :
   var t1 = document.getElementById('target1') 
   var t2 = document.getElementById('target2') 
   
   // 1) 사용할 하나의 함수 구현
   function btn_listener(event){
      switch(event.target.id){    // 이벤트를 발생시킨 타겟의 id 
         case 'target1' :
            alert(1) 
            break
         case 'target2' : 
            alert(2) 
            break 
      }
   } 
   // 2) 위의 함수를 사용하여 이벤트 적용 
   t1.addEventListener('click', btn_listener) 
   t2.addEventListener('click', btn_listener)
 */