package net.maromo.apirest.controller;

import net.maromo.apirest.model.Agenda;
import net.maromo.apirest.repository.AgendaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/agenda")
public class AgendaController {

    private final AgendaRepository agendaRepository;

    public AgendaController(AgendaRepository agendaRepository) {
        this.agendaRepository = agendaRepository;
    }

    @GetMapping
    public List<Agenda> listar(){
        return agendaRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Agenda adicionar(@RequestBody Agenda agenda){
        return agendaRepository.save(agenda);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Agenda> atualizar(@PathVariable("id") long id, @RequestBody Agenda agenda){
        Optional<Agenda> agendaData = agendaRepository.findById(id);
        if(agendaData.isPresent()){
            Agenda _agenda = agendaData.get();
            _agenda.setId(agenda.getId());
            _agenda.setNome(agenda.getNome());
            _agenda.setFone(agenda.getFone());
            _agenda.setEmail(agenda.getEmail());
            return new ResponseEntity<>(agendaRepository.save(_agenda), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<HttpStatus> deletar(@PathVariable("id") long id){
        try{
            agendaRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
