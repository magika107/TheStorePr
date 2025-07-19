package model.service;
import lombok.Getter;
import model.entity.Inventory;
import model.repository.InventoryRepository;


import java.util.List;

public class InventoryService implements Service<Inventory> {
    @Getter
    private static InventoryService service = new InventoryService();

    private InventoryService() {
    }

    @Override
    public void save(Inventory inventory) throws Exception {
        try (InventoryRepository inventoryRepository = new InventoryRepository()) {
            inventoryRepository.save(inventory);
        }
    }

    @Override
    public void edit(Inventory inventory) throws Exception {
        try (InventoryRepository inventoryRepository = new InventoryRepository()) {
            if (inventoryRepository.findById(inventory.getId()) != null) {
                inventoryRepository.save(inventory);
            } else {
                throw new Exception("Inventory with ID " + inventory.getId() + " not found");
            }
        }
    }

    @Override
    public void delete(int id) throws Exception {
        try (InventoryRepository inventoryRepository = new InventoryRepository()) {
            if (inventoryRepository.findById(id) != null) {
                inventoryRepository.delete(id);
            } else {
                throw new Exception("Inventory with ID " + id + " not found");
            }
        }
    }

    @Override
    public List<Inventory> findAll() throws Exception {
        try (InventoryRepository inventoryRepository = new InventoryRepository()) {
            return inventoryRepository.findAll();
        }
    }

    @Override
    public Inventory findById(int id) throws Exception {
        try (InventoryRepository inventoryRepository = new InventoryRepository()) {
            return inventoryRepository.findById(id);
        }
    }
}
