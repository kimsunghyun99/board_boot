package com.board.controller;



import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.File;
import javax.sql.DataSource;

import org.apache.ibatis.mapping.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.TinyBitSet;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.board.dto.BoardDTO;
import com.board.dto.FileDTO;
import com.board.service.BoardService;
import com.board.service.MasterService;
import com.board.service.MemberService;
import com.board.util.Page2;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class MasterController {
	@Autowired
	private final MasterService service;
	
	
	private final BoardService service1;
	
	
	private final DataSource dataSource;

	// 시스템관리 첫 페이지
	@GetMapping("/master/sysManage") 
		public void getSysMange() {
			
		}
	
	
	
	
	//전체 회원 숫자 보기
	@GetMapping("/master/cntMember")
	public void CountMember(Model model) {

		int cnt = service.CntMember();
		int wcnt =service.CntW();
		int mcnt = service.CntM();
		 
		
		System.out.println(wcnt);
		
		model.addAttribute("cnt", cnt);
		model.addAttribute("wcnt", wcnt);
		model.addAttribute("mcnt", mcnt);
		
		
	}
		
	// 게시물 개수 페이지
	@GetMapping("/master/cntPost") 
	public void CountPost(Model model) throws Exception {
		
		int postcnt = service.CntPost();
	    model.addAttribute("postcnt",postcnt);
		
	}



// 댓글 개수 페이지
@GetMapping("/master/cntReply") 
public void CountReply(Model model) throws Exception {
	
	int replycnt = service.CntReply();
    model.addAttribute("replycnt",replycnt);
	
}

//삭제된 파일 띄우기 
@GetMapping("/master/deleteFile") 
public void getDeleteFile(Model model) throws Exception {
	
	int deletepost =service.DeleteFilecnt();
	model.addAttribute("deletepost", deletepost);

	List<HashMap<String, String>> deletedFileNamesList = service.SeeDeleteFile();
	
	
	
	List<String> deletedFileNames = new ArrayList<String>();
	if (deletedFileNamesList != null) {
	for(HashMap<String, String> fileMap:deletedFileNamesList) {
		deletedFileNames.add(fileMap.get("ORG_FILENAME"));	 
	}
	}
	 model.addAttribute("deletedFileNames", deletedFileNames);
	
	 

	 // 개별삭제를 위해 fileseqno 가져오기  -- 실패 big deciaml???????
	// List<HashMap<String, Object>> deletedFileSeqnoList = service.GetFileseqno();
//	 List<BigDecimal> aa = new ArrayList<BigDecimal>();
//	 
 //List<Integer> deletedFileSeqno = new ArrayList<Integer>();

	 
	// System.out.println(deletedFileSeqnoList.get(0));
	// System.out.println(deletedFileSeqnoList.get(1));
	
//	 for(HashMap<String, Object> SeqnoMap:deletedFileSeqnoList) {
//		 deletedFileSeqno.add((int)SeqnoMap.get("FILESEQNO"));	 
//		 }
	  
	 
	 
	 
	// model.addAttribute("deletedFileSeqno", deletedFileSeqno);
	 
	
}	



//파일 완전히 삭제하기
@ResponseBody
@PostMapping("/master/deleteFile") 
public String postDeleteFile() throws Exception {
	
	 List<FileDTO> files = service.findNFile();
	 for(int i =0;i< files.size(); i++) {
         String filePath = "C:\\Repository\\file\\"+files.get(i).getStored_filename();
         
         File deleteFile = new File(filePath);
         if(deleteFile.exists()) {
            deleteFile.delete();
            service.DeleteAll();
         }else {
            System.out.println("에러"+files.get(i).getFileseqno());
         }
	 }
	 
         return "{\"message\" :\"GOOD\"}";
	
	
}




//게시판 관리 
@GetMapping("/master/boardManage") 
public void getboardmanage(Model model,@RequestParam(value="page", required=false, defaultValue="1") int pageNum,
		@RequestParam(name="keyword",defaultValue="",required=false) String keyword) throws Exception {
	
	int postNum = 5; //한 화면에 보여지는 게시물 행의 갯수
	int startPoint = (pageNum-1) * postNum+1; //페이지 시작 게시물 번호
	int endPoint = pageNum*postNum;
	int pageListCount = 5; //화면 하단에 보여지는 페이지리스트의 페이지 갯수		
	int totalCount = service1.getTotalCount(keyword); //전체 게시물 갯수	
	
	Page2 page = new Page2();

	model.addAttribute("list", service1.list(startPoint,endPoint,keyword));
	model.addAttribute("totalElement", service1.getTotalCount(keyword));
	model.addAttribute("page", pageNum);
	model.addAttribute("keyword", keyword);
	model.addAttribute("pageList", page.getPageList(pageNum, postNum, pageListCount,totalCount,keyword));
}



//게시판 관리 
@ResponseBody
@PostMapping("/master/boardManage") 
public String postboardmanage(@RequestBody Map<String,Object> params) throws Exception {

	
	try {
	int seqno = Integer.parseInt(params.get("seqno").toString());
	service.deletePost(seqno);
	
	}catch (Exception e) {
		e.printStackTrace();
        return "{\"message\":\"ERROR\"}";
    }
	return "{\"message\":\"GOOD\"}";
}



// 시스템 정보 페이지
@GetMapping("/master/sysinfo") 
public void getSysInfo(Model model) throws Exception {
	
	
	 DatabaseMetaData metaData = dataSource.getConnection().getMetaData();
	
	
	
	
String osName =System.getProperty("os.name");
String osVersion =System.getProperty("os.version");
String javaVersion =System.getProperty("java.version");
String jvmName =System.getProperty("java.vm.name");
String jvmVersion =System.getProperty("java.vm.version");
String dbDriver = metaData.getDriverName();
String dbDriverVersion = metaData.getDriverVersion();


model.addAttribute("osName", osName);
model.addAttribute("osVersion", osVersion);
model.addAttribute("javaVersion", javaVersion);
model.addAttribute("jvmName", jvmName);
model.addAttribute("jvmVersion", jvmVersion);
model.addAttribute("dbDriver", dbDriver);
model.addAttribute("dbDriverVersion", dbDriverVersion);



	
	
}








}
