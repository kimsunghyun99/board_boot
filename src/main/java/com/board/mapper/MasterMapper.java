package com.board.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.ognl.ASTBitNegate;

import com.board.dto.FileDTO;

@Mapper
public interface MasterMapper {

	
	
	
	
	// 회원 수 세기
		public int CntMember();
		
    //  여자 수 세기
		public int CntW();
		
		  //  남자 수 세기
				public int CntM();
		
		
		
		
		//댓글 수 세기
				public int CntReply() throws Exception;
				
				//게시물 전체 수 세기
			public int CntPost() throws Exception;
			
			 //파일 삭제 개수 세기
			public int DeleteFilecnt() throws Exception;
			
			
			// N파일 뽑기 
			public List<FileDTO> findNFile() throws Exception;
			
			
			// 삭제된 파일 명 출력
			public List<HashMap<String, String>> SeeDeleteFile() throws Exception;
			
			// 완전 삭제를 위해 fileseqno 가져오기
			public List<HashMap<String, Object>> GetFileseqno() throws Exception;
			
			
			// 파일 완전삭제
					public void DeleteFile() throws Exception;
		
				// 모든파일 완전삭제
					public void DeleteAll() throws Exception;
					
					
					// 마스터 권한 게시물 삭제
					public int deletePost(int seqno) throws Exception;
					
					
					
					
					
		
}
