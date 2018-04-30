package model;

import java.awt.Point;
import java.io.Serializable;

import model.enemy.Enemy;

public abstract class Path implements Serializable {
		/**
	 * 
	 */
	private static final long serialVersionUID = -1127733677422173458L;
		private boolean L, R, U, D;
		
		public Path() {
			this.L = false;
			this.R = false;
			this.U = false;
			this.D = false;
		}
		
		public boolean getL() {
			return this.L;
		}
		public boolean getR() {
			return this.R;
		}
		public boolean getU() {
			return this.U;
		}
		public boolean getD() {
			return this.D;
		}
		
		public void setL(boolean a) {
			this.L = a;
		}
		public void setR(boolean a) {
			this.R = a;
		}
		public void setU(boolean a) {
			this.U = a;
		}
		public void setD(boolean a) {
			this.D = a;
		}
		
		

		public abstract Point checkTurns(Point p, Enemy e);
}
