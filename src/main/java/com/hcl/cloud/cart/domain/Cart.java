package com.hcl.cloud.cart.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Cart entity class to be persisted in the database.
 *
 * @author baghelp
 */
@Entity
@Table(name = "CART")
public class Cart implements Serializable {

    /**
     * Serial version UID for the serialization of the object.
     */
    private static final long serialVersionUID = 4L;

    /**
     * Primary key - Id, field for the Cart entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_sequence")
    @SequenceGenerator(name = "cart_sequence", sequenceName = "CART_SEQ")
    @Column(name = "cart_id", unique = true, nullable = false)
    private long id;

    /**
     * user_id field of the database table "cart".
     */
    @Column(name = "USER_ID")
    private String userId;

    /**
     * cart_object field of the database table "cart".
     * It's a string type value that stores the string in json format in the database.
     */
    @Column(name = "CART_OBJECT")
    private String cartJson;

    /**
     * Getter method for id.
     * @return id
     */
    public long getId() {
        return id;
    }

    /**
     * Setter method for id.
     * @param id
     */
    public void setId(final long id) {
        this.id = id;
    }

    /**
     * Getter method for userId.
     * @return
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Setter method for userId.
     * @param userId
     */
    public void setUserId(final String userId) {
        this.userId = userId;
    }

    /**
     * Getter method for cartJson.
     * @return
     */
    public String getCartJson() {
        return cartJson;
    }

    /**
     * Setter method for cartJson.
     * @param cartJson
     */
    public void setCartJson(final String cartJson) {
        this.cartJson = cartJson;
    }

}
