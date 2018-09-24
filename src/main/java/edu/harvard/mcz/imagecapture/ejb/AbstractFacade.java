/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.harvard.mcz.imagecapture.ejb;

import edu.harvard.mcz.imagecapture.data.Collector;
import edu.harvard.mcz.imagecapture.data.Image;
import edu.harvard.mcz.imagecapture.data.LatLong;
import edu.harvard.mcz.imagecapture.data.Specimen;
import edu.harvard.mcz.imagecapture.data.Tracking;
import edu.harvard.mcz.imagecapture.data.Users;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CollectionJoin;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


/**
 *
 * @author mole
 */
@DeclareRoles(value = {"Administrator","Data entry","Full Access","Editor","Chief Editor"})
@RolesAllowed(value = {"Administrator","Data entry","Full Access","Editor","Chief Editor"})
public abstract class AbstractFacade<T> {

	private final static Logger logger = Logger.getLogger(AbstractFacade.class.getName());
	private Class<T> entityClass;

	public AbstractFacade(Class<T> entityClass) {
		this.entityClass = entityClass;
	}
	@EJB
	UsersFacadeLocal userBean;

	@RolesAllowed(value = {"Administrator", "Data entry", "Full Access", "Editor", "Chief Editor"})
	protected abstract EntityManager getEntityManager();

	@RolesAllowed(value = {"Administrator", "Data entry", "Full Access", "Editor", "Chief Editor"})
	public void create(T entity) {
		if (entity.getClass() == Specimen.class) {
		    logger.log(Level.INFO,"AbstractFacade.create " + ((Specimen)entity).getBarcode());
			Tracking newTracking = new Tracking();
			newTracking.setEventType(((Specimen) entity).getWorkFlowStatus());
			newTracking.setUsername(((Specimen) entity).getCreatedBy());
			newTracking.setSpecimenId((Specimen) entity);
			getEntityManager().persist(newTracking);
			getEntityManager().flush();
			getEntityManager().refresh(newTracking);
			((Specimen) entity).getTrackingCollection().add(newTracking);
			if (((Specimen) entity).getGeoreference()==null) { 
				LatLong georeference = new LatLong();
				georeference.setSpecimenid(((Specimen) entity));
				((Specimen) entity).setGeoreference(georeference);
			}
		}
		getEntityManager().persist(entity);
	}

	@RolesAllowed(value = {"Administrator", "Data entry", "Full Access", "Editor", "Chief Editor"})
	public boolean isManaged(T entity) {
		return getEntityManager().contains(entity);
	}
	
	@RolesAllowed(value = {"Administrator", "Data entry", "Full Access", "Editor", "Chief Editor"})
	public void edit(T entity) {
		if (entity.getClass() == Specimen.class) {
		    logger.log(Level.INFO,"AbstractFacade.edit " + ((Specimen)entity).getBarcode());
			String username = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
			Users user = userBean.findByName(username);
			((Specimen) entity).setLastUpdatedBy(user.getFullname());
			((Specimen) entity).setDateLastUpdated(new Date());
			Tracking newTracking = new Tracking();
			newTracking.setEventType(((Specimen) entity).getWorkFlowStatus());
			newTracking.setUsername(((Specimen) entity).getLastUpdatedBy());
			newTracking.setSpecimenId((Specimen) entity);
			getEntityManager().persist(newTracking);
			getEntityManager().flush();
			getEntityManager().refresh(newTracking);
			((Specimen) entity).getTrackingCollection().add(newTracking);
			if (((Specimen) entity).getGeoreference()==null) { 
				LatLong georeference = new LatLong();
				georeference.setSpecimenid(((Specimen) entity));
				((Specimen) entity).setGeoreference(georeference);
			}
		}
		getEntityManager().merge(entity);
		//getEntityManager().flush();
	}

    @RolesAllowed(value = {"Administrator","Data entry","Full Access","Editor","Chief Editor"})
	public void remove(T entity) {
		getEntityManager().remove(getEntityManager().merge(entity));
	}

	@RolesAllowed(value = {"Administrator", "Data entry", "Full Access", "Editor", "Chief Editor"})
	public T find(Object id) {
		return getEntityManager().find(entityClass, id);
	}
	
	@RolesAllowed(value = {"Administrator", "Data entry", "Full Access", "Editor", "Chief Editor"})
	public void flush(T entity) {
		getEntityManager().flush();
	}
	
	@RolesAllowed(value = {"Administrator", "Data entry", "Full Access", "Editor", "Chief Editor"})
	public List<T> findAll() {
		javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
		cq.select(cq.from(entityClass));
		return getEntityManager().createQuery(cq).getResultList();
	}

	@RolesAllowed(value = {"Administrator", "Data entry", "Full Access", "Editor", "Chief Editor"})
	public List<T> findRange(int[] range) {
		javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
		cq.select(cq.from(entityClass));
		javax.persistence.Query q = getEntityManager().createQuery(cq);
		q.setMaxResults(range[1] - range[0]);
		q.setFirstResult(range[0]);
		return q.getResultList();
	}

	@RolesAllowed(value = {"Administrator", "Data entry", "Full Access", "Editor", "Chief Editor"})
	public List<T> findRangeQuery(int[] range, String[] sortFields, boolean sortOrder, Map<String, String> filters) {
		return findRangeQueryAndOr(range, sortFields, sortOrder, filters, true);
	}

	/**
	 *
	 * @param range
	 * @param sortFields
	 * @param sortOrder
	 * @param filters
	 * @param useAnd true to assemble filters with and, false to assemble
	 * filters with or.
	 * @return
	 */
	@RolesAllowed(value = {"Administrator", "Data entry", "Full Access", "Editor", "Chief Editor"})
	public List<T> findRangeQueryAndOr(int[] range, String[] sortFields, boolean sortOrder, Map<String, String> filters, boolean useAnd) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
		cq.select(cq.from(entityClass));

		logger.log(Level.INFO, "findRangeQueryAndOr on " + entityClass.getName());
		
		//TODO: Execution is slow, observing 3-10 seconds on monitoring.

		Set<Root> roots = cq.getRoots();
		Root r = null;
		CollectionJoin<Specimen, Collector> join = null;
		CollectionJoin<Specimen, Image> joini = null;
		if (!roots.isEmpty()) {
			r = (Root) roots.toArray()[0];
			if (entityClass == Specimen.class) {
				if (filters.containsKey("collectorName") && filters.get("collectorName")!=null && !filters.get("collectorName").isEmpty()) {
					join = r.joinCollection("collectorCollection", JoinType.LEFT);
					logger.log(Level.INFO, "Joining Collector");
				}
				if (filters.containsKey("path") && filters.get("path")!=null && !filters.get("path").isEmpty()) {
					joini = r.joinCollection("imageCollection", JoinType.LEFT);
					logger.log(Level.INFO, "Joining Image");
				}				
			} else {
				logger.log(Level.INFO, "Not Specimen:" + entityClass.getName());
			}
		}
		if (sortOrder) {
			if (sortFields.length > 0) {
				List<Order> order = new ArrayList<Order>();
				for (int x = 0; x < sortFields.length; x++) {
					if (sortFields[x].equals("filename")  && entityClass.equals(Specimen.class) ) {
						 if (joini==null) { 
					        joini = r.joinCollection("imageCollection", JoinType.LEFT);
						 }
					     order.add(cb.asc(joini.get(sortFields[x])));
					     
					     logger.log(Level.INFO,"Adding: " + sortFields[x]);
					     
					     logger.log(Level.INFO, "Joining Images");
					} else { 
					     order.add(cb.asc(r.get(sortFields[x])));
					     logger.log(Level.INFO,"Adding: " + sortFields[x]);
					}
				}
				cq.orderBy(order);
				Iterator<Order> i = order.iterator();
				while (i.hasNext()) { 
					logger.log(Level.INFO,i.next().getExpression().getAlias());
				}
			}
		} else {
			if (sortFields.length > 0) {
				List<Order> order = new ArrayList<Order>();
				for (int x = 0; x < sortFields.length; x++) {
					if (sortFields[x].equals("filename") && entityClass.equals(Specimen.class)) { 
						 // if we are querying on specimen (not image), join out to the image table.
						 if (joini==null) { 
					         joini = r.joinCollection("imageCollection", JoinType.LEFT);
						 }
					     order.add(cb.asc(joini.get(sortFields[x])));
					     logger.log(Level.INFO, "Joining Images");
					     
					     logger.log(Level.INFO,"Adding: " + sortFields[x]);
					     
					} else { 
					     order.add(cb.desc(r.get(sortFields[x])));
					     logger.log(Level.INFO,"Adding: " + sortFields[x]);
					}
				}
				cq.orderBy(order);
				Iterator<Order> i = order.iterator();
				while (i.hasNext()) { 
					logger.log(Level.INFO,i.next().getExpression().getAlias());
				}
			}
		}
		cq = this.constructCriteriaQuery(filters, useAnd, cq, cb, r, join, joini);
		cq.distinct(true);
		javax.persistence.Query q = getEntityManager().createQuery(cq);
		//logger.log(Level.INFO, q.unwrap(QueryImpl.class).getDatabaseQuery().getSQLString());
		if (range.length>0) { 
		   q.setMaxResults(range[1] - range[0]);
		   q.setFirstResult(range[0]);
		} 
		return q.getResultList();
	}

	@RolesAllowed(value = {"Administrator", "Data entry", "Full Access", "Editor", "Chief Editor"})
	public int count() {
		CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
		javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
		cq.select(getEntityManager().getCriteriaBuilder().count(rt));
		javax.persistence.Query q = getEntityManager().createQuery(cq);
		return ((Long) q.getSingleResult()).intValue();
	}

	@RolesAllowed(value = {"Administrator", "Data entry", "Full Access", "Editor", "Chief Editor"})
	public int countFiltered(Map<String, String> filters) {
		int result = 0;
		if (filters == null) {
			result = count();
		} else {
			CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
			javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
			CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
			cq.select(cb.countDistinct(rt));
			// See: http://stackoverflow.com/questions/6197591/how-to-do-a-distinct-count-in-jpa-critera-api
			// Couldn't get subquery working.
			Set<Root> roots = cq.getRoots();
			Root r = null;
			CollectionJoin<Specimen, Collector> join = null;
			CollectionJoin<Specimen, Image> joini = null;
			if (!roots.isEmpty()) {
				r = (Root) roots.toArray()[0];
				if (entityClass == Specimen.class) {
					if (filters.containsKey("collectorName") && filters.get("collectorName")!=null) {
						join = r.joinCollection("collectorCollection", JoinType.LEFT);
						logger.log(Level.INFO, "Joining Collector");
					}
					if (filters.containsKey("path") && filters.get("path")!=null && !filters.get("path").isEmpty()) {
						joini = r.joinCollection("imageCollection", JoinType.LEFT);
						logger.log(Level.INFO, "Joining Image");
					}						
				} else {
					logger.log(Level.INFO, "Not Specimen:" + entityClass.getName());
				}
			}

			cq = this.constructCriteriaQuery(filters, true, cq, cb, r, join, joini);
		    cq.distinct(true);
			javax.persistence.Query q = getEntityManager().createQuery(cq);
			// Should work, but just logs a null string.  See: http://antoniogoncalves.org/2012/05/24/how-to-get-the-jpqlsql-string-from-a-criteriaquery-in-jpa/
			// logger.log(Level.INFO, q.unwrap(EJBQueryImpl.class).getDatabaseQuery().getSQLString());
			result = ((Long) q.getSingleResult()).intValue();
		}
		return result;
	}
	
	private CriteriaQuery constructCriteriaQuery(Map<String, String> filters, boolean useAnd, CriteriaQuery cq, CriteriaBuilder cb, Root r, CollectionJoin<Specimen, Collector> join, CollectionJoin<Specimen, Image> joini) { 
		if (filters != null) {
			Iterator<Entry<String, String>> i = filters.entrySet().iterator();
			Predicate predicate = null;
			boolean hasTerms = false;
			while (i.hasNext()) {
				Entry<String, String> e = i.next();
				if (r != null) {
					// logger.log(Level.INFO, e.getKey() + " " + e.getValue());
					if (e.getValue().length() > 0) {
						boolean invert = false;
						if (e.getValue().contains("*")) {
							e.setValue(e.getValue().replace('*', '%'));
						}
						if (e.getValue().startsWith("!")) {
							e.setValue(e.getValue().substring(1, e.getValue().length()));
							invert = true;
						}
						if (e.getValue().contains("%")) {
							if (hasTerms && predicate != null) {
								if (useAnd) {
									try {
										if (invert) {  
										    predicate = cb.and(predicate, cb.notLike(cb.lower(r.get(e.getKey())), e.getValue().toLowerCase()));
									    } else { 
										    predicate = cb.and(predicate, cb.like(cb.lower(r.get(e.getKey())), e.getValue().toLowerCase()));
									    }
									} catch (IllegalArgumentException ex) {
										try { 
										if (invert) {  
										    predicate = cb.and(predicate, cb.notLike(cb.lower((Expression) join.get(e.getKey())), e.getValue().toLowerCase()));
										} else { 
										    predicate = cb.and(predicate, cb.like(cb.lower((Expression) join.get(e.getKey())), e.getValue().toLowerCase()));
										}
										} catch (IllegalArgumentException ex1) { 
								        } catch (NullPointerException ex1) { }
										try { 
										if (invert) {  
										    predicate = cb.and(predicate, cb.notLike(cb.lower((Expression) joini.get(e.getKey())), e.getValue().toLowerCase()));
										} else { 
										    predicate = cb.and(predicate, cb.like(cb.lower((Expression) joini.get(e.getKey())), e.getValue().toLowerCase()));
										}
										} catch (IllegalArgumentException ex1) { 
								        } catch (NullPointerException ex1) { }
									}
								} else {
									try {
										if (invert) {  
										    predicate = cb.or(predicate, cb.notLike(cb.lower(r.get(e.getKey())), e.getValue().toLowerCase()));
										} else { 
										    predicate = cb.or(predicate, cb.like(cb.lower(r.get(e.getKey())), e.getValue().toLowerCase()));
										}
									} catch (IllegalArgumentException ex) {
										try { 
										if (invert) {  
										    predicate = cb.or(predicate, cb.notLike(cb.lower((Expression) join.get(e.getKey())), e.getValue().toLowerCase()));
										} else { 
										    predicate = cb.or(predicate, cb.like(cb.lower((Expression) join.get(e.getKey())), e.getValue().toLowerCase()));
										}
										} catch (IllegalArgumentException ex1) { 
								        } catch (NullPointerException ex1) { }
										try {
											if (invert) {  
											    predicate = cb.or(predicate, cb.notLike(cb.lower((Expression) joini.get(e.getKey())), e.getValue().toLowerCase()));
											} else { 
											    predicate = cb.or(predicate, cb.like(cb.lower((Expression) joini.get(e.getKey())), e.getValue().toLowerCase()));
											}
										} catch (IllegalArgumentException ex1) { 
								        } catch (NullPointerException ex1) { }
									}
								}
							} else {
								try {
									if (invert) {  
									    predicate = cb.notLike(cb.lower(r.get(e.getKey())), e.getValue().toLowerCase());
									} else { 
									    predicate = cb.like(cb.lower(r.get(e.getKey())), e.getValue().toLowerCase());
									}
								} catch (IllegalArgumentException ex) {
									try { 
									if (invert) {  
									    predicate = cb.like(cb.lower((Expression) join.get(e.getKey())), e.getValue().toLowerCase());
									} else {
									    predicate = cb.like(cb.lower((Expression) join.get(e.getKey())), e.getValue().toLowerCase());
									}
									} catch (IllegalArgumentException ex1) { 
							        } catch (NullPointerException ex1) { }
									try { 
										if (invert) {  
										    predicate = cb.like(cb.lower((Expression) joini.get(e.getKey())), e.getValue().toLowerCase());
										} else {
										    predicate = cb.like(cb.lower((Expression) joini.get(e.getKey())), e.getValue().toLowerCase());
										}
									} catch (IllegalArgumentException ex1) { 
							        } catch (NullPointerException ex1) { }
								}
							}
							hasTerms = true;
							//cq.where(cb.like(r.get(e.getKey()), e.getValue())) ;
						} else if (e.getValue().equals("null")) {
							if (hasTerms && predicate != null) {
								if (useAnd) {
									try {
										predicate = cb.and(predicate, cb.or( cb.isNull(r.get(e.getKey())), cb.equal(r.get(e.getKey()), "")));
									} catch (IllegalArgumentException ex) {
										try { 
										    predicate = cb.and(predicate, cb.or( cb.isNull(join.get(e.getKey())), cb.equal(join.get(e.getKey()), "")));
										} catch (IllegalArgumentException ex1) { 
								        } catch (NullPointerException ex1) { }
										try { 
											predicate = cb.and(predicate, cb.or( cb.isNull(joini.get(e.getKey())), cb.equal(joini.get(e.getKey()), "")));
										} catch (IllegalArgumentException ex1) { 
								        } catch (NullPointerException ex1) { }
									}
								} else {
									try {
										predicate = cb.or(predicate, cb.or( cb.isNull(r.get(e.getKey())), cb.equal(r.get(e.getKey()), "")));
									} catch (IllegalArgumentException ex) {
										try { 
										   predicate = cb.or(predicate, cb.or( cb.isNull(join.get(e.getKey())), cb.equal(join.get(e.getKey()), "")));
										} catch (IllegalArgumentException ex1) { 
								        } catch (NullPointerException ex1) { }
										try { 
										   predicate = cb.or(predicate, cb.or( cb.isNull(joini.get(e.getKey())), cb.equal(joini.get(e.getKey()), "")));
										} catch (IllegalArgumentException ex1) { 
								        } catch (NullPointerException ex1) { }
									}
								}
							} else {
								try {
									predicate = cb.or(cb.isNull(r.get(e.getKey())), cb.equal(r.get(e.getKey()), ""));
								} catch (IllegalArgumentException ex) {
									try { 
									   predicate = cb.or(cb.isNull(join.get(e.getKey())), cb.equal(join.get(e.getKey()), ""));
									} catch (IllegalArgumentException ex1) { 
							        } catch (NullPointerException ex1) { }
									try {
									   predicate = cb.or(cb.isNull(joini.get(e.getKey())), cb.equal(joini.get(e.getKey()), ""));
									} catch (IllegalArgumentException ex1) { 
							        } catch (NullPointerException ex1) { }
								}
							}
							hasTerms = true;
						} else {
							if (hasTerms && predicate != null) {
								if (useAnd) {
									try {
									    if (invert) {  
										   predicate = cb.and(predicate, cb.notEqual(cb.lower(r.get(e.getKey())), e.getValue().toLowerCase()));
									    } else { 
										    predicate = cb.and(predicate, cb.equal(cb.lower(r.get(e.getKey())), e.getValue().toLowerCase()));
									    }
									} catch (IllegalArgumentException ex) {
									    if (invert) { 
									    	try {
										       predicate = cb.and(predicate, cb.notEqual(cb.lower((Expression) join.get(e.getKey())), e.getValue().toLowerCase()));
									    	} catch (IllegalArgumentException ex1) { 
									        } catch (NullPointerException ex1) { }
									    	try {
											   predicate = cb.and(predicate, cb.notEqual(cb.lower((Expression) joini.get(e.getKey())), e.getValue().toLowerCase()));
									    	} catch (IllegalArgumentException ex1) { 
									        } catch (NullPointerException ex1) { }
									    } else {
									    	try { 
										        predicate = cb.and(predicate, cb.equal(cb.lower((Expression) join.get(e.getKey())), e.getValue().toLowerCase()));
									    	} catch (IllegalArgumentException ex1) { 
									        } catch (NullPointerException ex1) { }
									    	try { 
										        predicate = cb.and(predicate, cb.equal(cb.lower((Expression) joini.get(e.getKey())), e.getValue().toLowerCase()));
									    	} catch (IllegalArgumentException ex1) { 
									        } catch (NullPointerException ex1) { }
									    }
									}
								} else {
									// Special case handling for comma separated list of barcodes for images.
									if (e.getKey().equals("magic_ImageBarcodeList")) {
										String[] barcodes = e.getValue().split("[,; ]", 100);
										if (barcodes != null) {
											for (int x = 0; x < barcodes.length; x++) {
												predicate = cb.or(predicate, cb.equal(r.get("rawExifBarcode"), barcodes[x]));
												predicate = cb.or(predicate, cb.equal(r.get("rawBarcode"), barcodes[x]));
											}
										}
									} else {
										if (e.getKey().equals("magic_SpecimenBarcodeList")) {
											String[] barcodes = e.getValue().split("[,; ]", 100);
											if (barcodes != null) {
												for (int x = 0; x < barcodes.length; x++) {
													predicate = cb.or(predicate, cb.equal(r.get("barcode"), barcodes[x]));
												}
											}
										} else {
											// Normal general purpose case
											try {
												if (invert) { 
												    predicate = cb.or(predicate, cb.notEqual(cb.lower(r.get(e.getKey())), e.getValue().toLowerCase()));
												} else {    
												    predicate = cb.or(predicate, cb.equal(cb.lower(r.get(e.getKey())), e.getValue().toLowerCase()));
												}
											} catch (IllegalArgumentException ex) {
												try { 
												if (invert) { 
												    predicate = cb.or(predicate, cb.notEqual(cb.lower((Expression) join.get(e.getKey())), e.getValue().toLowerCase()));
												} else { 
												    predicate = cb.or(predicate, cb.equal(cb.lower((Expression) join.get(e.getKey())), e.getValue().toLowerCase()));
												}
												} catch (IllegalArgumentException ex1) { 
										        } catch (NullPointerException ex1) { }
												try { 
													if (invert) { 
													    predicate = cb.or(predicate, cb.notEqual(cb.lower((Expression) joini.get(e.getKey())), e.getValue().toLowerCase()));
													} else { 
													    predicate = cb.or(predicate, cb.equal(cb.lower((Expression) joini.get(e.getKey())), e.getValue().toLowerCase()));
													}
												} catch (IllegalArgumentException ex1) { 
											    } catch (NullPointerException ex1) { }
											}
										}
									}
								}
							} else {
								// Special case handling for comma separated list of barcodes for images.
								if (e.getKey().equals("magic_ImageBarcodeList")) {
									String[] barcodes = e.getValue().split("[,; ]", 100);
									if (barcodes != null) {
										for (int x = 0; x < barcodes.length; x++) {
											if (hasTerms) {
												predicate = cb.or(predicate, cb.equal(r.get("rawExifBarcode"), barcodes[x]));
												predicate = cb.or(predicate, cb.equal(r.get("rawBarcode"), barcodes[x]));
											} else {
												// first instance, just use cb.equal, all others use cb.or(predicate,cb.equal())
												predicate = cb.equal(r.get("rawExifBarcode"), barcodes[x]);
												predicate = cb.or(predicate, cb.equal(r.get("rawBarcode"), barcodes[x]));
											}
											hasTerms = true;
										}
									}
								} else {
									if (e.getKey().equals("magic_SpecimenBarcodeList")) {
										String[] barcodes = e.getValue().split("[,; ]", 100);
										if (barcodes != null) {
											for (int x = 0; x < barcodes.length; x++) {
												if (hasTerms) {
													predicate = cb.or(predicate, cb.equal(r.get("barcode"), barcodes[x]));
												} else {
													// first instance, just use cb.equal, all others use cb.or(predicate,cb.equal())
													predicate = cb.equal(r.get("barcode"), barcodes[x]);
												}
												hasTerms = true;
											}
										}
									} else {
										logger.log(Level.INFO, e.getKey());
										logger.log(Level.INFO, e.getValue());
										logger.log(Level.INFO, e.getValue().toLowerCase());
										// Normal general purpose case
										try {
											if (invert) { 
											    predicate = cb.notEqual(cb.lower(r.get(e.getKey())), e.getValue().toLowerCase());
											} else {
											    predicate = cb.equal(cb.lower(r.get(e.getKey())), e.getValue().toLowerCase());
											}
										} catch (IllegalArgumentException ex) {
											if (join==null && joini==null) { 
												logger.log(Level.SEVERE, ex.getMessage(), ex);
											} else { 
												try { 
											        predicate = cb.equal(cb.lower((Expression)join.get(e.getKey())), e.getValue().toLowerCase());
										        } catch (IllegalArgumentException ex1) { 
										        } catch (NullPointerException ex1) { }
												try { 
											        predicate = cb.equal(cb.lower((Expression)joini.get(e.getKey())), e.getValue().toLowerCase());
										        } catch (IllegalArgumentException ex1) { 
										        } catch (NullPointerException ex1) { }
											    
											}
										}
									}
								}
							}
							hasTerms = true;
							//cq.where(cb.equal(r.get(e.getKey()), e.getValue())) ;
						}
					}
				}
			}
			if (hasTerms) {
				cq.where(predicate);
			}

		}
		return cq;
	}
	
}
