package cocozzang.toyproject.source.service;

import cocozzang.toyproject.source.dto.BoardDTO;
import cocozzang.toyproject.source.entity.BoardEntity;
import cocozzang.toyproject.source.repository.BoardRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public Long boardWrite(BoardDTO boardDTO) {

        BoardEntity boardEntity = new BoardEntity();

        boardEntity.setTitle(boardDTO.getTitle());
        boardEntity.setWriter(boardDTO.getWriter());
        boardEntity.setContent(boardDTO.getContent());
        boardEntity.setRegDate(String.valueOf(LocalDate.now()));
        boardEntity.setModDate(String.valueOf(LocalDate.now()));

        System.out.println("XXXX :: " + boardEntity);

        return boardRepository.save(boardEntity).getId();
    }

    public List<BoardDTO> boardTotal() {
        List<BoardEntity> boardEntities = boardRepository.findAll();
        List<BoardDTO> boardDTOS = new ArrayList<>();
        for(int i = 0; i < boardEntities.size(); i++) {
            boardDTOS.add(BoardDTO.entityToDTO(boardEntities.get(i)));
        }
        return boardDTOS;
    }

    public BoardDTO boardDetail(Long bid) {
        return BoardDTO.entityToDTO(boardRepository.findById(bid).get());
    }

    public void boardDelete(Long bid) {
        boardRepository.deleteById(bid);
    }

    public void boardUpdate(BoardDTO boardDTO) {
        BoardEntity boardEntity = new BoardEntity();

        boardEntity.setId(boardDTO.getBoardId());
        boardEntity.setWriter(boardDTO.getWriter());
        boardEntity.setTitle(boardDTO.getTitle());
        boardEntity.setContent(boardDTO.getContent());
        boardEntity.setRegDate(boardDTO.getRegTime());
        boardEntity.setModDate(boardDTO.getModTime());

        boardRepository.save(boardEntity);
    }
}
