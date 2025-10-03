package vn.iostar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.iostar.entity.Product;
import vn.iostar.repository.ProductRepository;
import java.util.List;
@Service
public class ProductServiceImpl implements ProductServices {
    @Autowired
    private ProductRepository repo;
    @Override
    public List<Product> listAll() { return repo.findAll(); }
    @Override
    public Product save(Product product) { return repo.save(product); }
    @Override
    public Product get(Long id) { return repo.findById(id).get(); }
    @Override
    public void delete(Long id) { repo.deleteById(id); }
}