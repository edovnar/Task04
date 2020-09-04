package sb.service;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public class GeneralService<E> {

    protected JpaRepository<E, Integer> jpaRepo;

    public GeneralService(JpaRepository<E, Integer> jpaRepo){
        this.jpaRepo = jpaRepo;
    }


    public List<E> getAll(){ return jpaRepo.findAll(); }

    public E getById(Integer id){
        return (E) jpaRepo.findById(id);
    }

    public void save(E entity){
        jpaRepo.save(entity);
    }

    public void delete(E entity){
        jpaRepo.delete(entity);
    };

    public void deleteById(Integer id){
        jpaRepo.deleteById(id);
    }
}
