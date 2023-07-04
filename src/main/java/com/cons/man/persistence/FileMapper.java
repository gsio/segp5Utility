package com.cons.man.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cons.man.domain.FileVO;

@Repository(value = "FileMapper")
public interface FileMapper {

	public List<FileVO> getFileList(FileVO vo);

	public void insertFile(FileVO vo);

	public void updateFile(FileVO vo);

	public void deleteFile(String virtname);

}