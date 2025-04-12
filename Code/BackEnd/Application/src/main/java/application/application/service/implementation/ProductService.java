package application.application.service.implementation;

import application.application.DTO.ProductDTO;
import application.application.mapper.ProductMapper;
import application.application.model.Category;
import application.application.model.Product;
import application.application.repository.CategoryRepository;
import application.application.repository.ProductRepository;
import application.application.service.interfaces.IProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService implements IProductService {

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<ProductDTO> getAllProductsDTO() {
        return ProductMapper.productListToDTOList(productRepository.findAll());
    }


    @Override
    public Product addProduct(String name, String description, double price, String imageUrl, String categoryName) {
        Category category = categoryRepository.findByName(categoryName);

        if (category == null) throw new RuntimeException("Category not found");

        Product product = Product.builder()
                .name(name)
                .description(description)
                .price(price)
                .imageUrl(imageUrl)
                .category(category)
                .build();

        return productRepository.save(product);
    }

    public Product addProduct(Product noCategoryProduct, String categoryName) {
        Category category = categoryRepository.findByName(categoryName);

        if (category == null) throw new RuntimeException("Category not found");

        noCategoryProduct.setCategory(category);
        return productRepository.save(noCategoryProduct);
    }

    @Override
    public Product getProductById(String id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Override
    public ProductDTO getProductDTOById(String id) {
        return ProductMapper.productToDTO(getProductById(id));
    }
}
