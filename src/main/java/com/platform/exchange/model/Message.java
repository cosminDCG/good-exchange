package com.platform.exchange.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "message")
public class Message implements Serializable {

    @Id
    private UUID id;

    @ManyToOne
    private User from;

    @ManyToOne
    private User to;

    private String content;

    private Date date;

    public Message() {
    }

    public Message(UUID id, User from, User to, String content, Date date) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.content = content;
        this.date = date;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public User getTo() {
        return to;
    }

    public void setTo(User to) {
        this.to = to;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(id, message.id) &&
                Objects.equals(from, message.from) &&
                Objects.equals(to, message.to) &&
                Objects.equals(content, message.content) &&
                Objects.equals(date, message.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, from, to, content, date);
    }
}
