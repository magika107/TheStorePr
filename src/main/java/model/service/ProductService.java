package model.service;

import model.entity.Product;
import model.repository.ProductRepository;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class ProductService implements Service<Product> {
    @Override
    public void save(Product product) throws Exception {
        try (ProductRepository productRepository = new ProductRepository()) {
            productRepository.save(product);
        }
    }

    @Override
    public void edit(Product product) throws Exception {
        try (ProductRepository productRepository = new ProductRepository()) {
            if (productRepository.findById(product.getId()) != null) {
                productRepository.edit(product);
            } else {
                throw new Exception("Product not found");
            }
        }

    }

    @Override
    public void delete(int id) throws Exception {
        try (ProductRepository productRepository = new ProductRepository()) {
            if (productRepository.findById(id) != null) {
                productRepository.delete(id);
            } else {
                throw new Exception("Product not found");
            }
        }

    }

    @Override
    public List<Product> findAll() throws Exception {
        try (ProductRepository productRepository = new ProductRepository()) {
            return productRepository.findAll();
        }
    }

    @Override
    public Product findById(int id) throws Exception {
        try (ProductRepository productRepository = new ProductRepository()) {
            return productRepository.findById(id);
        }
    }

    public Product findBySerialNumber(String serialNumber) throws Exception {
        try (ProductRepository productRepository = new ProductRepository()) {
            return productRepository.findBySerialNumber(serialNumber);
        }
    }

    public List<Product> findByBrandAndModel(String brand, String model) throws Exception {
        try (ProductRepository productRepository = new ProductRepository()) {
            return productRepository.findByBrandAndModel(brand, model);
        }
    }

    @Override
    public void close() throws Exception {

    }
}
