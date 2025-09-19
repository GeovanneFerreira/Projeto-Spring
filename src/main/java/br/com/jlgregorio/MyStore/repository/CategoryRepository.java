package br.com.jlgregorio.MyStore.repository;

import br.com.jlgregorio.MyStore.model.CategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryModel, Long> {

    public List<CategoryModel> findByNameContainsIgnoreCaseOrderByName(String name);

}
