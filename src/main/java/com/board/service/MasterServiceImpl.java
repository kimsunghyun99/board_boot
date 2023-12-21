package com.board.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.board.dto.FileDTO;
import com.board.mapper.MasterMapper;
import com.board.mapper.MemberMapper;

@Service
public class MasterServiceImpl implements MasterService  {

	@Autowired
	private MasterMapper mapper; 
	
	
	// 회원 수 세기
		@Override
			public int CntMember() {
				return mapper.CntMember();
			}
		
		
		// 여자 수 세기
				@Override
					public int CntW() {
						return mapper.CntW();
					}
				
				// 남자 수 세기
				@Override
					public int CntM() {
						return mapper.CntM();
					}
		
		
	
		//댓글 수 세기
		@Override
		public int CntReply() throws Exception {
			
			return mapper.CntReply();
		}

		
		// 전체 개시물 수 세기
		@Override
		public int CntPost() throws Exception {
			// TODO Auto-generated method stub
			return mapper.CntPost();
		}

		
		 //파일 삭제 개수 세기
		@Override
		public int DeleteFilecnt() throws Exception {
			
			return mapper.DeleteFilecnt();
		}

		// 삭제된 파일 명 출력
		@Override
		public List<HashMap<String, String>> SeeDeleteFile() throws Exception {
			 
			return mapper.SeeDeleteFile();
			
		}
		
		// 완전 삭제를 위해 fileseqno 가져오기
		@Override
		public List<HashMap<String, Object>> GetFileseqno() throws Exception {
			return mapper.GetFileseqno();
		}
		

		// 파일 완전삭제
		@Override
		public void DeleteFile() throws Exception {
			mapper.DeleteFile();
			
		}

		// 모든 파일 완전삭제
		@Override
		public void DeleteAll() throws Exception {
			mapper.DeleteAll();
			
		}

		// 마스터 권한 게시물 삭제
		@Override
		public int deletePost(int seqno) throws Exception {
			
			
			return mapper.deletePost(seqno);
			
		}

		// N파일 뽑기   
		@Override
		public List<FileDTO> findNFile() throws Exception {
			
			return mapper.findNFile();
		}
	
}
