package com.example.ecommerceweb.controller;

import com.example.ecommerceweb.dto.ProductDTO;
import com.example.ecommerceweb.model.*;
import com.example.ecommerceweb.repository.RoleRepository;
import com.example.ecommerceweb.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {
    public static String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/productImages";
    @Autowired
    SalesAgentService salesAgentService;
    @Autowired
    SupplierService supplierService;
    @Autowired
    BranchService branchService;
    @Autowired
    DriverService driverService;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    ProductService productService;
    @Autowired
    RoleRepository roleRepository;
    @GetMapping("/admin")
    public String adminHome() {
        return "adminHome";
    }


    @RequestMapping("/admin/salesAgent")
    public String viewSalesAgents(Model model, @Param("keyword") String keyword) {
        List<Employee> listSaleAgent = salesAgentService.listAll(keyword);
        model.addAttribute("employees", listSaleAgent);
        model.addAttribute("keyword", keyword);
        return "salesAgent";
    }

    @RequestMapping("/admin/suppliers")
    public String viewSupplier(Model model, @Param("keyword") String keyword) {
        List<Supplier> listSuppliers = supplierService.lisAll(keyword);
        model.addAttribute("suppliers", listSuppliers);
        model.addAttribute("keyword", keyword);

        return "suppliers";
    }

    @RequestMapping("/admin/drivers")
    public String viewDriver(Model model, @Param("keyword") String keyword) {
        List<Driver> listDriver = driverService.lisAll(keyword);
        model.addAttribute("employees", listDriver);
        model.addAttribute("keyword", keyword);
        return "drivers";
    }

    @GetMapping("/admin/salesAgent/add")
    public String getAddEmployee(Model model) {
        model.addAttribute("employee", new Employee());
        return "salesAgentAdd";
    }

    @GetMapping("/admin/driver/add")
    public String getAddDriver(Model model) {
        model.addAttribute("employee", new Driver());
        return "driverAdd";
    }

    @GetMapping("/admin/suppliers/add")
    public String getAddSupplier(Model model) {
        model.addAttribute("employee", new Supplier());
        return "supplierAdd";
    }

    @PostMapping("/admin/salesAgent/add")
    public String addEmployee(@ModelAttribute("employee") Employee employee, HttpServletRequest request) throws ServletException {
        String password = employee.getPassword();
        employee.setPassword(bCryptPasswordEncoder.encode(password));
        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.findById(3).get());
        employee.setRoles(roles);
        salesAgentService.addEmployee(employee);
        return "redirect:/admin/salesAgent";
    }

    @PostMapping("/admin/driver/add")
    public String addDriver(@ModelAttribute("employee") Driver driver, HttpServletRequest request) throws ServletException {
        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.findById(5).get());
        driver.setRoles(roles);
        driverService.addDriver(driver);
        return "redirect:/admin/drivers";
    }

    @PostMapping("/admin/suppliers/add")
    public String addSupplier(@ModelAttribute("employee") Supplier supplier, HttpServletRequest request) throws ServletException {
        String password = supplier.getPassword();
        supplier.setPassword(bCryptPasswordEncoder.encode(password));
        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.findById(4).get());
        supplier.setRoles(roles);
        supplierService.addSupplier(supplier);
        return "redirect:/admin/suppliers";
    }

    @GetMapping("/admin/salesAgent/delete/{id}")
    public String deleteEmployee(@PathVariable int id) {
        salesAgentService.removeEmployeeById(id);
        return "redirect:/admin/salesAgent";
    }

    @GetMapping("/admin/driver/delete/{id}")
    public String deleteDriver(@PathVariable int id) {
        driverService.removeDriver(id);
        return "redirect:/admin/drivers";
    }

    @GetMapping("/admin/suppliers/delete/{id}")
    public String deleteSupplier(@PathVariable int id) {
        supplierService.removeSupplierById(id);
        return "redirect:/admin/suppliers";
    }

    @GetMapping("/admin/salesAgent/update/{id}")
    public String getUpdateEmployees(@PathVariable("id") int id, Model model) {
        Employee employee = salesAgentService.getEmployeeById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + id));

        model.addAttribute("employee", employee);
        return "updateSalesAgent";
    }

    @GetMapping("/admin/driver/update/{id}")
    public String getUpdateDriver(@PathVariable("id") int id, Model model) {
        Driver driver = driverService.getDriverById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + id));

        model.addAttribute("employee", driver);
        return "updateDriver";
    }

    @GetMapping("/admin/suppliers/update/{id}")
    public String getUpdateSuppliers(@PathVariable("id") int id, Model model) {
        Supplier supplier = supplierService.getSupplierById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + id));

        model.addAttribute("employee", supplier);
        return "updateSupplier";
    }

    @PostMapping("/admin/salesAgent/update/{id}")
    public String updateEmployee(@PathVariable("id") int id, @ModelAttribute("employee") Employee employee,
                                 BindingResult result, Model model) {
        if (result.hasErrors()) {
            employee.setId(id);
            return "updateSalesAgent";
        }
        String password = employee.getPassword();
        employee.setPassword(bCryptPasswordEncoder.encode(password));
        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.findById(3).get());
        employee.setRoles(roles);
        salesAgentService.addEmployee(employee);
        return "redirect:/admin/salesAgent";
    }

    @PostMapping("/admin/driver/update/{id}")
    public String updateDriver(@PathVariable("id") int id, @ModelAttribute("employee") Driver driver,
                                 BindingResult result, Model model) {
        if (result.hasErrors()) {
            driver.setId(id);
            return "updateDriver";
        }
        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.findById(5).get());
        driver.setRoles(roles);
        driverService.addDriver(driver);
        return "redirect:/admin/drivers";
    }

    @PostMapping("/admin/suppliers/update/{id}")
    public String updateSupplier(@PathVariable("id") int id, @ModelAttribute("employee") Supplier supplier,
                                 BindingResult result, Model model) {
        if (result.hasErrors()) {
            supplier.setId(id);
            return "updateSupplier";
        }
        String password = supplier.getPassword();
        supplier.setPassword(bCryptPasswordEncoder.encode(password));
        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.findById(4).get());
        supplier.setRoles(roles);
        supplierService.addSupplier(supplier);
        return "redirect:/admin/suppliers";
    }

    //Product Section
    @RequestMapping("/admin/products")
    public String viewProduct(Model model, @Param("keyword") String keyword) {
        List<Product> listProduct = productService.lisAll(keyword);
        model.addAttribute("products", listProduct);
        model.addAttribute("keyword", keyword);
        return "products";
    }

    @GetMapping("/admin/products/add")
    public String getAddProducts(Model model) {
        model.addAttribute("productDTO", new ProductDTO());
        model.addAttribute("branches", branchService.getAllBranch());
        return "productsAdd";
    }

    @PostMapping("/admin/products/add")
    public String addproduct(@ModelAttribute("productDTO")ProductDTO productDTO,
                             @RequestParam("productImage")MultipartFile file,
                             @RequestParam("imgName") String imgName) throws IOException {

        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setDescription(productDTO.getDescription());
        product.setBranch(branchService.getBranchById(productDTO.getBranchId()).get());
        product.setQuantity(productDTO.getQuantity());

        String imageUUID;
        if (!file.isEmpty()) {
            imageUUID = file.getOriginalFilename();
            Path fileNameAndPath = Paths.get(uploadDir, imageUUID);
            Files.write(fileNameAndPath, file.getBytes());
        }else {
            imageUUID = imgName;
        }

        product.setImageName(imageUUID);
        productService.addProduct(product);

        return "redirect:/admin/products";
    }

    @GetMapping("/admin/product/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.removeProductById(id);
        return "redirect:/admin/products";
    }
    @GetMapping("/admin/product/update/{id}")
    public String updateProduct(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id).get();
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setPrice(product.getPrice());
        productDTO.setDescription(product.getDescription());
        productDTO.setBranchId(product.getBranch().getId());
        productDTO.setQuantity(product.getQuantity());
        productDTO.setImageName(product.getImageName());

        model.addAttribute("productDTO", productDTO);
        model.addAttribute("branches", branchService.getAllBranch());

        return "productsAdd";
    }
}
