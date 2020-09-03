package sb.service;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public class Service<E> {

    JpaRepository<E, Integer> jpaRepo;

    public Service(JpaRepository<E, Integer> jpaRepo){
        this.jpaRepo = jpaRepo;
    }

    public List<E> getAll(){
        return jpaRepo.findAll();
    };

    public Optional<E> getById(Integer id){
        return jpaRepo.findById(id);
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
