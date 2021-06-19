package com.iktpreobuka.project.entities;
//• id, offer name, offer description, offer created, offer expires, regular 
//price, action price, image path, available offers, bought offers i offer 
//status
//• atribut offer created podrazumeva datum kreiranja ponude, a offer 
//expires datum kada ponuda ističe
//• atribut available offers govori koliko je trenutno ponuda na
//raspolaganju (broj dostupnih ponuda), dok atribut bought offers 
//govori koliko je ponuda dosad prodato (broj prodatih ponuda)
//• atribut image path podrazumeva putanju do slike i treba da bude
//tekstualnog tipa
//• offer status može da ima sledeće vrednosti: 
//WAIT_FOR_APPROVING, APPROVED, DECLINED i
//EXPIRED (koristiti enumeraciju)


import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class OfferEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "offer_id")
	private Integer id;
	@Column(name = "available_offers")
	private Integer availableOffers;
	@Column(name = "bought_offers")
	private Integer boughtOffers;
	@Column(name = "offer_name")
	private String offerName;
	@Column(name = "offer_desc")
	private String offerDesc;
	@Column(name = "offer_expires")
	private LocalDate offerExpires;
	@Column(name = "offer_created")
	private LocalDate offerCreated;
	@Column(name = "image_path")
	private String imagePath;
	@Column(name = "regular_price")
	private double regularPrice;
	@Column(name = "action_price")
	private double actionPrice;
	@Column(name = "offer_status")
	private EUofferStatus offerStatus;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "category")
	private CategoryEntity category;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "user")
	private UserEntity user;
	
	@OneToMany(mappedBy = "offer", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<BillEntity> bills;
	
	@OneToMany(mappedBy = "offer", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<VoucherEntity> vouchers;
	
	public OfferEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OfferEntity(Integer id, Integer availableOffers, Integer boughtOffers, String offerName, String offerDesc,
			LocalDate offerExpires, LocalDate offerCreated, String imagePath, double regularPrice, double actionPrice,
			EUofferStatus offerStatus, CategoryEntity category, UserEntity user, List<BillEntity> bills,
			List<VoucherEntity> vouchers) {
		super();
		this.id = id;
		this.availableOffers = availableOffers;
		this.boughtOffers = boughtOffers;
		this.offerName = offerName;
		this.offerDesc = offerDesc;
		this.offerExpires = offerExpires;
		this.offerCreated = offerCreated;
		this.imagePath = imagePath;
		this.regularPrice = regularPrice;
		this.actionPrice = actionPrice;
		this.offerStatus = offerStatus;
		this.category = category;
		this.user = user;
		this.bills = bills;
		this.vouchers = vouchers;
	}

	public List<VoucherEntity> getVouchers() {
		return vouchers;
	}

	public void setVouchers(List<VoucherEntity> vouchers) {
		this.vouchers = vouchers;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getAvailableOffers() {
		return availableOffers;
	}
	public void setAvailableOffers(Integer availableOffers) {
		this.availableOffers = availableOffers;
	}
	public Integer getBoughtOffers() {
		return boughtOffers;
	}
	public void setBoughtOffers(Integer boughtOffers) {
		this.boughtOffers = boughtOffers;
	}
	public String getOfferName() {
		return offerName;
	}
	public void setOfferName(String offerName) {
		this.offerName = offerName;
	}
	public String getOfferDesc() {
		return offerDesc;
	}
	public void setOfferDesc(String offerDesc) {
		this.offerDesc = offerDesc;
	}
	public LocalDate getOfferExpires() {
		return offerExpires;
	}
	public void setOfferExpires(LocalDate offerExpires) {
		this.offerExpires = offerExpires;
	}
	public LocalDate getOfferCreated() {
		return offerCreated;
	}
	public void setOfferCreated(LocalDate offerCreated) {
		this.offerCreated = offerCreated;
	}
	public double getRegularPrice() {
		return regularPrice;
	}
	public void setRegularPrice(double regularPrice) {
		this.regularPrice = regularPrice;
	}
	public double getActionPrice() {
		return actionPrice;
	}
	public void setActionPrice(double actionPrice) {
		this.actionPrice = actionPrice;
	}
	public EUofferStatus getOfferStatus() {
		return offerStatus;
	}
	public void setOfferStatus(EUofferStatus offerStatus) {
		this.offerStatus = offerStatus;
	}

	public CategoryEntity getCategory() {
		return category;
	}

	public void setCategory(CategoryEntity category) {
		this.category = category;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}


	public List<BillEntity> getBills() {
		return bills;
	}


	public void setBills(List<BillEntity> bills) {
		this.bills = bills;
	}
		
	
	
}
