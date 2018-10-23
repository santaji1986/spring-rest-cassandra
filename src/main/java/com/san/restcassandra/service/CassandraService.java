package com.san.restcassandra.service;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.san.restcassandra.model.Customer;
import com.san.restcassandra.model.User;
import com.san.restcassandra.repo.ReactiveCustomerRepository;
import com.san.restcassandra.repo.UserRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CassandraService {
    @Autowired
    private ReactiveCustomerRepository reactiveCustomerRepository;
    @Autowired
    private UserRepository userRepository;

    public Flux<Customer> getCustomers() {
        Flux<Customer> customers = null;
        customers = reactiveCustomerRepository.findAll();
        return customers;
    }

    public Flux<User> getUsers() {

        return userRepository.findAll();
    }

    public Mono<Customer> insertCustomerRecord(Long id, String firstName, String lastName, int age) {
        final Customer customer = new Customer();
        customer.setId(id);
        customer.setFirstname(firstName);
        customer.setLastname(lastName);
        customer.setAge(age);
        final Mono<Customer> cus = reactiveCustomerRepository.save(customer);
        return cus;
    }

    public Mono<User> insertUserRecord(Long id, String username, String password) {
        final User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setPassword(password);
        final Mono<User> userRecord = userRepository.save(user);

        return userRecord;
    }

    public List<User> processExcelFile(String filename) {
        final List<User> dictionary = getRecordsFromExcelFile(filename);
        System.out.println("CassandraService.processExcelFile() dictionary : " + dictionary);
        for (final User user : dictionary) {
            System.out.println("CassandraService.processExcelFile() UserRecord : " + user);
            insertUserRecord(user.getId(), user.getUsername(), user.getPassword());
            // userRepository.save(user);
        }
        // userRepository.saveAll(dictionary);
        return dictionary;

    }

    private List<User> getRecordsFromExcelFile(String filename) {
        final List<User> dictionary = new ArrayList<>();
        try (final FileInputStream excelFile = new FileInputStream(new File(filename)); final Workbook workbook = new XSSFWorkbook(excelFile)) {

            final Sheet datatypeSheet = workbook.getSheetAt(0);
            if (null != datatypeSheet) {
                final int physicalNumberOfRows = datatypeSheet.getPhysicalNumberOfRows();
                final List<String> columns = getListOfColumnNames(datatypeSheet);
                System.out.println("CassandraService.getRecordsFromExcelFile() " + columns);

                for (int i = 1; i < physicalNumberOfRows; i++) {
                    final Row currentRow = datatypeSheet.getRow(i);

                    dictionary.add(getRowList(columns, currentRow));
                }
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return dictionary;
    }

    private List<String> getListOfColumnNames(final Sheet datatypeSheet) {
        final List<String> columns = new ArrayList<>();
        final Row row = datatypeSheet.getRow(0);
        final Iterator<Cell> iterator2 = row.iterator();
        while (iterator2.hasNext()) {
            final Cell currentCell = iterator2.next();
            columns.add(currentCell.getStringCellValue());
        }
        return columns;
    }

    private User getRowList(final List<String> columns, final Row currentRow) {
        int columnCount = 0;
        final User user = new User();
        final Iterator<Cell> cellIterator = currentRow.iterator();
        while (cellIterator.hasNext()) {
            final Cell currentCell = cellIterator.next();
            if (currentCell.getCellType() == CellType.STRING) {
                final String stringCellValue = currentCell.getStringCellValue();
                if ("username".equalsIgnoreCase(columns.get(columnCount))) {
                    user.setUsername(stringCellValue);
                } else if ("password".equalsIgnoreCase(columns.get(columnCount))) {
                    user.setPassword(stringCellValue);
                }
            } else if (currentCell.getCellType() == CellType.NUMERIC) {
                final double numericCellValue = currentCell.getNumericCellValue();
                if ("id".equalsIgnoreCase(columns.get(columnCount))) {
                    final Long id = Math.round(numericCellValue);
                    user.setId(id);
                }
            }
            columnCount++;
        }
        return user;
    }

}
