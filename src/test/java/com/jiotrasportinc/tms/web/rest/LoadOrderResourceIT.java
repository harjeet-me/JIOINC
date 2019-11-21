package com.jiotrasportinc.tms.web.rest;

import com.jiotrasportinc.tms.JiotmsApp;
import com.jiotrasportinc.tms.domain.LoadOrder;
import com.jiotrasportinc.tms.repository.LoadOrderRepository;
import com.jiotrasportinc.tms.repository.search.LoadOrderSearchRepository;
import com.jiotrasportinc.tms.service.LoadOrderService;
import com.jiotrasportinc.tms.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

import static com.jiotrasportinc.tms.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.jiotrasportinc.tms.domain.enumeration.StatusEnum;
import com.jiotrasportinc.tms.domain.enumeration.COVEREDBY;
import com.jiotrasportinc.tms.domain.enumeration.LoadType;
import com.jiotrasportinc.tms.domain.enumeration.SizeEnum;
/**
 * Integration tests for the {@link LoadOrderResource} REST controller.
 */
@SpringBootTest(classes = JiotmsApp.class)
public class LoadOrderResourceIT {

    private static final String DEFAULT_ORDER_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ORDER_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_SHIPMENT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_SHIPMENT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_BOL = "AAAAAAAAAA";
    private static final String UPDATED_BOL = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_PICKUP = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PICKUP = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DROP = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DROP = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_PICKUP_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_PICKUP_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_DROP_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_DROP_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_CURRENT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_CURRENT_LOCATION = "BBBBBBBBBB";

    private static final StatusEnum DEFAULT_STATUS = StatusEnum.PICKEDUP;
    private static final StatusEnum UPDATED_STATUS = StatusEnum.ONROAD;

    private static final Long DEFAULT_DETENTION = 1L;
    private static final Long UPDATED_DETENTION = 2L;

    private static final Instant DEFAULT_CHASIS_IN_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CHASIS_IN_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final byte[] DEFAULT_POD = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_POD = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_POD_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_POD_CONTENT_TYPE = "image/png";

    private static final Boolean DEFAULT_HAZMAT = false;
    private static final Boolean UPDATED_HAZMAT = true;

    private static final String DEFAULT_RECIEVED_BY = "AAAAAAAAAA";
    private static final String UPDATED_RECIEVED_BY = "BBBBBBBBBB";

    private static final COVEREDBY DEFAULT_COVERED_BY = COVEREDBY.CompanyDriver;
    private static final COVEREDBY UPDATED_COVERED_BY = COVEREDBY.OwnerOperator;

    private static final LoadType DEFAULT_LOAD_TYPE = LoadType.REEFER;
    private static final LoadType UPDATED_LOAD_TYPE = LoadType.FLATBED;

    private static final SizeEnum DEFAULT_CONTAINER_SIZE = SizeEnum.C53;
    private static final SizeEnum UPDATED_CONTAINER_SIZE = SizeEnum.C43;

    private static final Integer DEFAULT_NUMBERS_OF_CONTAINER = 1;
    private static final Integer UPDATED_NUMBERS_OF_CONTAINER = 2;

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    @Autowired
    private LoadOrderRepository loadOrderRepository;

    @Autowired
    private LoadOrderService loadOrderService;

    /**
     * This repository is mocked in the com.jiotrasportinc.tms.repository.search test package.
     *
     * @see com.jiotrasportinc.tms.repository.search.LoadOrderSearchRepositoryMockConfiguration
     */
    @Autowired
    private LoadOrderSearchRepository mockLoadOrderSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restLoadOrderMockMvc;

    private LoadOrder loadOrder;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LoadOrderResource loadOrderResource = new LoadOrderResource(loadOrderService);
        this.restLoadOrderMockMvc = MockMvcBuilders.standaloneSetup(loadOrderResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LoadOrder createEntity(EntityManager em) {
        LoadOrder loadOrder = new LoadOrder()
            .orderNumber(DEFAULT_ORDER_NUMBER)
            .description(DEFAULT_DESCRIPTION)
            .shipmentNumber(DEFAULT_SHIPMENT_NUMBER)
            .bol(DEFAULT_BOL)
            .pickup(DEFAULT_PICKUP)
            .drop(DEFAULT_DROP)
            .pickupLocation(DEFAULT_PICKUP_LOCATION)
            .dropLocation(DEFAULT_DROP_LOCATION)
            .currentLocation(DEFAULT_CURRENT_LOCATION)
            .status(DEFAULT_STATUS)
            .detention(DEFAULT_DETENTION)
            .chasisInTime(DEFAULT_CHASIS_IN_TIME)
            .pod(DEFAULT_POD)
            .podContentType(DEFAULT_POD_CONTENT_TYPE)
            .hazmat(DEFAULT_HAZMAT)
            .recievedBy(DEFAULT_RECIEVED_BY)
            .coveredBy(DEFAULT_COVERED_BY)
            .loadType(DEFAULT_LOAD_TYPE)
            .containerSize(DEFAULT_CONTAINER_SIZE)
            .numbersOfContainer(DEFAULT_NUMBERS_OF_CONTAINER)
            .comments(DEFAULT_COMMENTS);
        return loadOrder;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LoadOrder createUpdatedEntity(EntityManager em) {
        LoadOrder loadOrder = new LoadOrder()
            .orderNumber(UPDATED_ORDER_NUMBER)
            .description(UPDATED_DESCRIPTION)
            .shipmentNumber(UPDATED_SHIPMENT_NUMBER)
            .bol(UPDATED_BOL)
            .pickup(UPDATED_PICKUP)
            .drop(UPDATED_DROP)
            .pickupLocation(UPDATED_PICKUP_LOCATION)
            .dropLocation(UPDATED_DROP_LOCATION)
            .currentLocation(UPDATED_CURRENT_LOCATION)
            .status(UPDATED_STATUS)
            .detention(UPDATED_DETENTION)
            .chasisInTime(UPDATED_CHASIS_IN_TIME)
            .pod(UPDATED_POD)
            .podContentType(UPDATED_POD_CONTENT_TYPE)
            .hazmat(UPDATED_HAZMAT)
            .recievedBy(UPDATED_RECIEVED_BY)
            .coveredBy(UPDATED_COVERED_BY)
            .loadType(UPDATED_LOAD_TYPE)
            .containerSize(UPDATED_CONTAINER_SIZE)
            .numbersOfContainer(UPDATED_NUMBERS_OF_CONTAINER)
            .comments(UPDATED_COMMENTS);
        return loadOrder;
    }

    @BeforeEach
    public void initTest() {
        loadOrder = createEntity(em);
    }

    @Test
    @Transactional
    public void createLoadOrder() throws Exception {
        int databaseSizeBeforeCreate = loadOrderRepository.findAll().size();

        // Create the LoadOrder
        restLoadOrderMockMvc.perform(post("/api/load-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loadOrder)))
            .andExpect(status().isCreated());

        // Validate the LoadOrder in the database
        List<LoadOrder> loadOrderList = loadOrderRepository.findAll();
        assertThat(loadOrderList).hasSize(databaseSizeBeforeCreate + 1);
        LoadOrder testLoadOrder = loadOrderList.get(loadOrderList.size() - 1);
        assertThat(testLoadOrder.getOrderNumber()).isEqualTo(DEFAULT_ORDER_NUMBER);
        assertThat(testLoadOrder.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testLoadOrder.getShipmentNumber()).isEqualTo(DEFAULT_SHIPMENT_NUMBER);
        assertThat(testLoadOrder.getBol()).isEqualTo(DEFAULT_BOL);
        assertThat(testLoadOrder.getPickup()).isEqualTo(DEFAULT_PICKUP);
        assertThat(testLoadOrder.getDrop()).isEqualTo(DEFAULT_DROP);
        assertThat(testLoadOrder.getPickupLocation()).isEqualTo(DEFAULT_PICKUP_LOCATION);
        assertThat(testLoadOrder.getDropLocation()).isEqualTo(DEFAULT_DROP_LOCATION);
        assertThat(testLoadOrder.getCurrentLocation()).isEqualTo(DEFAULT_CURRENT_LOCATION);
        assertThat(testLoadOrder.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testLoadOrder.getDetention()).isEqualTo(DEFAULT_DETENTION);
        assertThat(testLoadOrder.getChasisInTime()).isEqualTo(DEFAULT_CHASIS_IN_TIME);
        assertThat(testLoadOrder.getPod()).isEqualTo(DEFAULT_POD);
        assertThat(testLoadOrder.getPodContentType()).isEqualTo(DEFAULT_POD_CONTENT_TYPE);
        assertThat(testLoadOrder.isHazmat()).isEqualTo(DEFAULT_HAZMAT);
        assertThat(testLoadOrder.getRecievedBy()).isEqualTo(DEFAULT_RECIEVED_BY);
        assertThat(testLoadOrder.getCoveredBy()).isEqualTo(DEFAULT_COVERED_BY);
        assertThat(testLoadOrder.getLoadType()).isEqualTo(DEFAULT_LOAD_TYPE);
        assertThat(testLoadOrder.getContainerSize()).isEqualTo(DEFAULT_CONTAINER_SIZE);
        assertThat(testLoadOrder.getNumbersOfContainer()).isEqualTo(DEFAULT_NUMBERS_OF_CONTAINER);
        assertThat(testLoadOrder.getComments()).isEqualTo(DEFAULT_COMMENTS);

        // Validate the LoadOrder in Elasticsearch
        verify(mockLoadOrderSearchRepository, times(1)).save(testLoadOrder);
    }

    @Test
    @Transactional
    public void createLoadOrderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = loadOrderRepository.findAll().size();

        // Create the LoadOrder with an existing ID
        loadOrder.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLoadOrderMockMvc.perform(post("/api/load-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loadOrder)))
            .andExpect(status().isBadRequest());

        // Validate the LoadOrder in the database
        List<LoadOrder> loadOrderList = loadOrderRepository.findAll();
        assertThat(loadOrderList).hasSize(databaseSizeBeforeCreate);

        // Validate the LoadOrder in Elasticsearch
        verify(mockLoadOrderSearchRepository, times(0)).save(loadOrder);
    }


    @Test
    @Transactional
    public void getAllLoadOrders() throws Exception {
        // Initialize the database
        loadOrderRepository.saveAndFlush(loadOrder);

        // Get all the loadOrderList
        restLoadOrderMockMvc.perform(get("/api/load-orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(loadOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].orderNumber").value(hasItem(DEFAULT_ORDER_NUMBER)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].shipmentNumber").value(hasItem(DEFAULT_SHIPMENT_NUMBER)))
            .andExpect(jsonPath("$.[*].bol").value(hasItem(DEFAULT_BOL)))
            .andExpect(jsonPath("$.[*].pickup").value(hasItem(DEFAULT_PICKUP.toString())))
            .andExpect(jsonPath("$.[*].drop").value(hasItem(DEFAULT_DROP.toString())))
            .andExpect(jsonPath("$.[*].pickupLocation").value(hasItem(DEFAULT_PICKUP_LOCATION)))
            .andExpect(jsonPath("$.[*].dropLocation").value(hasItem(DEFAULT_DROP_LOCATION)))
            .andExpect(jsonPath("$.[*].currentLocation").value(hasItem(DEFAULT_CURRENT_LOCATION)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].detention").value(hasItem(DEFAULT_DETENTION.intValue())))
            .andExpect(jsonPath("$.[*].chasisInTime").value(hasItem(DEFAULT_CHASIS_IN_TIME.toString())))
            .andExpect(jsonPath("$.[*].podContentType").value(hasItem(DEFAULT_POD_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].pod").value(hasItem(Base64Utils.encodeToString(DEFAULT_POD))))
            .andExpect(jsonPath("$.[*].hazmat").value(hasItem(DEFAULT_HAZMAT.booleanValue())))
            .andExpect(jsonPath("$.[*].recievedBy").value(hasItem(DEFAULT_RECIEVED_BY)))
            .andExpect(jsonPath("$.[*].coveredBy").value(hasItem(DEFAULT_COVERED_BY.toString())))
            .andExpect(jsonPath("$.[*].loadType").value(hasItem(DEFAULT_LOAD_TYPE.toString())))
            .andExpect(jsonPath("$.[*].containerSize").value(hasItem(DEFAULT_CONTAINER_SIZE.toString())))
            .andExpect(jsonPath("$.[*].numbersOfContainer").value(hasItem(DEFAULT_NUMBERS_OF_CONTAINER)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)));
    }
    
    @Test
    @Transactional
    public void getLoadOrder() throws Exception {
        // Initialize the database
        loadOrderRepository.saveAndFlush(loadOrder);

        // Get the loadOrder
        restLoadOrderMockMvc.perform(get("/api/load-orders/{id}", loadOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(loadOrder.getId().intValue()))
            .andExpect(jsonPath("$.orderNumber").value(DEFAULT_ORDER_NUMBER))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.shipmentNumber").value(DEFAULT_SHIPMENT_NUMBER))
            .andExpect(jsonPath("$.bol").value(DEFAULT_BOL))
            .andExpect(jsonPath("$.pickup").value(DEFAULT_PICKUP.toString()))
            .andExpect(jsonPath("$.drop").value(DEFAULT_DROP.toString()))
            .andExpect(jsonPath("$.pickupLocation").value(DEFAULT_PICKUP_LOCATION))
            .andExpect(jsonPath("$.dropLocation").value(DEFAULT_DROP_LOCATION))
            .andExpect(jsonPath("$.currentLocation").value(DEFAULT_CURRENT_LOCATION))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.detention").value(DEFAULT_DETENTION.intValue()))
            .andExpect(jsonPath("$.chasisInTime").value(DEFAULT_CHASIS_IN_TIME.toString()))
            .andExpect(jsonPath("$.podContentType").value(DEFAULT_POD_CONTENT_TYPE))
            .andExpect(jsonPath("$.pod").value(Base64Utils.encodeToString(DEFAULT_POD)))
            .andExpect(jsonPath("$.hazmat").value(DEFAULT_HAZMAT.booleanValue()))
            .andExpect(jsonPath("$.recievedBy").value(DEFAULT_RECIEVED_BY))
            .andExpect(jsonPath("$.coveredBy").value(DEFAULT_COVERED_BY.toString()))
            .andExpect(jsonPath("$.loadType").value(DEFAULT_LOAD_TYPE.toString()))
            .andExpect(jsonPath("$.containerSize").value(DEFAULT_CONTAINER_SIZE.toString()))
            .andExpect(jsonPath("$.numbersOfContainer").value(DEFAULT_NUMBERS_OF_CONTAINER))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS));
    }

    @Test
    @Transactional
    public void getNonExistingLoadOrder() throws Exception {
        // Get the loadOrder
        restLoadOrderMockMvc.perform(get("/api/load-orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLoadOrder() throws Exception {
        // Initialize the database
        loadOrderService.save(loadOrder);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockLoadOrderSearchRepository);

        int databaseSizeBeforeUpdate = loadOrderRepository.findAll().size();

        // Update the loadOrder
        LoadOrder updatedLoadOrder = loadOrderRepository.findById(loadOrder.getId()).get();
        // Disconnect from session so that the updates on updatedLoadOrder are not directly saved in db
        em.detach(updatedLoadOrder);
        updatedLoadOrder
            .orderNumber(UPDATED_ORDER_NUMBER)
            .description(UPDATED_DESCRIPTION)
            .shipmentNumber(UPDATED_SHIPMENT_NUMBER)
            .bol(UPDATED_BOL)
            .pickup(UPDATED_PICKUP)
            .drop(UPDATED_DROP)
            .pickupLocation(UPDATED_PICKUP_LOCATION)
            .dropLocation(UPDATED_DROP_LOCATION)
            .currentLocation(UPDATED_CURRENT_LOCATION)
            .status(UPDATED_STATUS)
            .detention(UPDATED_DETENTION)
            .chasisInTime(UPDATED_CHASIS_IN_TIME)
            .pod(UPDATED_POD)
            .podContentType(UPDATED_POD_CONTENT_TYPE)
            .hazmat(UPDATED_HAZMAT)
            .recievedBy(UPDATED_RECIEVED_BY)
            .coveredBy(UPDATED_COVERED_BY)
            .loadType(UPDATED_LOAD_TYPE)
            .containerSize(UPDATED_CONTAINER_SIZE)
            .numbersOfContainer(UPDATED_NUMBERS_OF_CONTAINER)
            .comments(UPDATED_COMMENTS);

        restLoadOrderMockMvc.perform(put("/api/load-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLoadOrder)))
            .andExpect(status().isOk());

        // Validate the LoadOrder in the database
        List<LoadOrder> loadOrderList = loadOrderRepository.findAll();
        assertThat(loadOrderList).hasSize(databaseSizeBeforeUpdate);
        LoadOrder testLoadOrder = loadOrderList.get(loadOrderList.size() - 1);
        assertThat(testLoadOrder.getOrderNumber()).isEqualTo(UPDATED_ORDER_NUMBER);
        assertThat(testLoadOrder.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testLoadOrder.getShipmentNumber()).isEqualTo(UPDATED_SHIPMENT_NUMBER);
        assertThat(testLoadOrder.getBol()).isEqualTo(UPDATED_BOL);
        assertThat(testLoadOrder.getPickup()).isEqualTo(UPDATED_PICKUP);
        assertThat(testLoadOrder.getDrop()).isEqualTo(UPDATED_DROP);
        assertThat(testLoadOrder.getPickupLocation()).isEqualTo(UPDATED_PICKUP_LOCATION);
        assertThat(testLoadOrder.getDropLocation()).isEqualTo(UPDATED_DROP_LOCATION);
        assertThat(testLoadOrder.getCurrentLocation()).isEqualTo(UPDATED_CURRENT_LOCATION);
        assertThat(testLoadOrder.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testLoadOrder.getDetention()).isEqualTo(UPDATED_DETENTION);
        assertThat(testLoadOrder.getChasisInTime()).isEqualTo(UPDATED_CHASIS_IN_TIME);
        assertThat(testLoadOrder.getPod()).isEqualTo(UPDATED_POD);
        assertThat(testLoadOrder.getPodContentType()).isEqualTo(UPDATED_POD_CONTENT_TYPE);
        assertThat(testLoadOrder.isHazmat()).isEqualTo(UPDATED_HAZMAT);
        assertThat(testLoadOrder.getRecievedBy()).isEqualTo(UPDATED_RECIEVED_BY);
        assertThat(testLoadOrder.getCoveredBy()).isEqualTo(UPDATED_COVERED_BY);
        assertThat(testLoadOrder.getLoadType()).isEqualTo(UPDATED_LOAD_TYPE);
        assertThat(testLoadOrder.getContainerSize()).isEqualTo(UPDATED_CONTAINER_SIZE);
        assertThat(testLoadOrder.getNumbersOfContainer()).isEqualTo(UPDATED_NUMBERS_OF_CONTAINER);
        assertThat(testLoadOrder.getComments()).isEqualTo(UPDATED_COMMENTS);

        // Validate the LoadOrder in Elasticsearch
        verify(mockLoadOrderSearchRepository, times(1)).save(testLoadOrder);
    }

    @Test
    @Transactional
    public void updateNonExistingLoadOrder() throws Exception {
        int databaseSizeBeforeUpdate = loadOrderRepository.findAll().size();

        // Create the LoadOrder

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLoadOrderMockMvc.perform(put("/api/load-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loadOrder)))
            .andExpect(status().isBadRequest());

        // Validate the LoadOrder in the database
        List<LoadOrder> loadOrderList = loadOrderRepository.findAll();
        assertThat(loadOrderList).hasSize(databaseSizeBeforeUpdate);

        // Validate the LoadOrder in Elasticsearch
        verify(mockLoadOrderSearchRepository, times(0)).save(loadOrder);
    }

    @Test
    @Transactional
    public void deleteLoadOrder() throws Exception {
        // Initialize the database
        loadOrderService.save(loadOrder);

        int databaseSizeBeforeDelete = loadOrderRepository.findAll().size();

        // Delete the loadOrder
        restLoadOrderMockMvc.perform(delete("/api/load-orders/{id}", loadOrder.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LoadOrder> loadOrderList = loadOrderRepository.findAll();
        assertThat(loadOrderList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the LoadOrder in Elasticsearch
        verify(mockLoadOrderSearchRepository, times(1)).deleteById(loadOrder.getId());
    }

    @Test
    @Transactional
    public void searchLoadOrder() throws Exception {
        // Initialize the database
        loadOrderService.save(loadOrder);
        when(mockLoadOrderSearchRepository.search(queryStringQuery("id:" + loadOrder.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(loadOrder), PageRequest.of(0, 1), 1));
        // Search the loadOrder
        restLoadOrderMockMvc.perform(get("/api/_search/load-orders?query=id:" + loadOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(loadOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].orderNumber").value(hasItem(DEFAULT_ORDER_NUMBER)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].shipmentNumber").value(hasItem(DEFAULT_SHIPMENT_NUMBER)))
            .andExpect(jsonPath("$.[*].bol").value(hasItem(DEFAULT_BOL)))
            .andExpect(jsonPath("$.[*].pickup").value(hasItem(DEFAULT_PICKUP.toString())))
            .andExpect(jsonPath("$.[*].drop").value(hasItem(DEFAULT_DROP.toString())))
            .andExpect(jsonPath("$.[*].pickupLocation").value(hasItem(DEFAULT_PICKUP_LOCATION)))
            .andExpect(jsonPath("$.[*].dropLocation").value(hasItem(DEFAULT_DROP_LOCATION)))
            .andExpect(jsonPath("$.[*].currentLocation").value(hasItem(DEFAULT_CURRENT_LOCATION)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].detention").value(hasItem(DEFAULT_DETENTION.intValue())))
            .andExpect(jsonPath("$.[*].chasisInTime").value(hasItem(DEFAULT_CHASIS_IN_TIME.toString())))
            .andExpect(jsonPath("$.[*].podContentType").value(hasItem(DEFAULT_POD_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].pod").value(hasItem(Base64Utils.encodeToString(DEFAULT_POD))))
            .andExpect(jsonPath("$.[*].hazmat").value(hasItem(DEFAULT_HAZMAT.booleanValue())))
            .andExpect(jsonPath("$.[*].recievedBy").value(hasItem(DEFAULT_RECIEVED_BY)))
            .andExpect(jsonPath("$.[*].coveredBy").value(hasItem(DEFAULT_COVERED_BY.toString())))
            .andExpect(jsonPath("$.[*].loadType").value(hasItem(DEFAULT_LOAD_TYPE.toString())))
            .andExpect(jsonPath("$.[*].containerSize").value(hasItem(DEFAULT_CONTAINER_SIZE.toString())))
            .andExpect(jsonPath("$.[*].numbersOfContainer").value(hasItem(DEFAULT_NUMBERS_OF_CONTAINER)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)));
    }
}
